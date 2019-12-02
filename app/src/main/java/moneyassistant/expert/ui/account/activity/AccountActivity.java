package moneyassistant.expert.ui.account.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.Wallet;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.ui.account.viewmodel.AccountViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

public class AccountActivity extends AppCompatActivity {

    public static final String ACCOUNT_ID = "account_id";

    @BindView(R.id.wallet_name_input)
    EditText walletName;
    @BindView(R.id.wallet_type_input)
    Spinner walletType;
    @BindView(R.id.wallet_current_amount_input)
    EditText walletAmount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    IntelViewModelFactory factory;
    private Unbinder unbinder;
    private AccountViewModel viewModel;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        unbinder = ButterKnife.bind(this);
        MoneyAssistant.getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, factory).get(AccountViewModel.class);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String[] walletArray = Wallet.makeArrayFromValues();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, walletArray);
        walletType.setAdapter(arrayAdapter);
        long id = getIntent().getLongExtra(ACCOUNT_ID, 0);
        viewModel.findAccountById(id).observe(this, this::consumeAccount);
    }

    @OnClick(R.id.save)
    void onSave() {
        if (walletName.getText().toString().equals("")) {
            Toast.makeText(this, R.string.no_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (walletAmount.getText().toString().equals("")) {
            Toast.makeText(this, R.string.no_amount, Toast.LENGTH_SHORT).show();
            return;
        }
        if (account == null) {
            account = new Account();
            account.setCurrentAmount(Double.parseDouble(walletAmount.getText().toString()));
            account.setStartingAmount(Double.parseDouble(walletAmount.getText().toString()));
        }
        account.setName(walletName.getText().toString());
        account.setType(Wallet.valueOf(walletType.getSelectedItemPosition()));
        viewModel.save(account);
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.confirm_delete);
            builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                viewModel.delete(account);
                onBackPressed();
            });
            builder.setNegativeButton(R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorRed));
        }
        return super.onOptionsItemSelected(item);
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void consumeAccount(Account account) {
        if (account != null) {
            this.account = account;
            walletAmount.setEnabled(false);
            walletName.setText(account.getName());
            walletType.setSelection(Arrays.asList(Wallet.makeArrayFromValues())
                    .indexOf(account.getType()));
            walletAmount.setText(String.valueOf(account.getCurrentAmount()));
        }
    }

}
