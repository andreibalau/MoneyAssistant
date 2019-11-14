package moneyassistant.expert.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnCheckModelCount;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.viewmodel.AccountViewModel;

public class AccountActivity extends AppCompatActivity implements OnCheckModelCount {

    private EditText walletName;
    private Spinner walletType;
    private EditText walletAmount;

    private long id;

    private AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.setOnCheckModelCount(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        walletType = findViewById(R.id.wallet_type_input);
        walletName = findViewById(R.id.wallet_name_input);
        walletAmount = findViewById(R.id.wallet_current_amount_input);
        String[] walletArray = getResources().getStringArray(R.array.group_account);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, walletArray);
        walletType.setAdapter(arrayAdapter);
        id = getIntent().getLongExtra(Constants.resourceId, 0);
        if (id == 0) {
            setTitle(R.string.add_new_wallet);
        } else {
            walletAmount.setEnabled(false);
            accountViewModel.getAccountById(id).observe(this, account -> {
                if (account != null) {
                    walletName.setText(account.getName());
                    walletAmount.setText(String.valueOf(account.getCurrentAmount()));
                    walletType.setSelection(Arrays.asList(Constants.WALLET_TYPES)
                            .indexOf(account.getType()));
                }
            });
        }
    }

    public void save(View view) {
        if (walletName.getText().toString().equals("")) {
            Toast.makeText(this, R.string.no_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (walletAmount.getText().toString().equals("")) {
            Toast.makeText(this, R.string.no_amount, Toast.LENGTH_SHORT).show();
            return;
        }
        Account account = new Account();
        account.setName(walletName.getText().toString());
        account.setType(Constants.WALLET_TYPES[walletType.getSelectedItemPosition()]);
        if (id == 0) {
            account.setCurrentAmount(Double.parseDouble(walletAmount.getText().toString()));
            account.setStartingAmount(Double.parseDouble(walletAmount.getText().toString()));
            accountViewModel.insert(account);
        } else {
            account.setId(id);
            accountViewModel.update(account);
        }
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Util.createDialogWithButtons(this, R.string.confirm_delete,
                        (dialogInterface, i) -> accountViewModel.checkTransactions(id));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheck(int count) {
        if (count > 1) {
            Util.createDialog(this, R.string.error_del);
        } else {
            accountViewModel.delete(id);
            onBackPressed();
        }
    }
}
