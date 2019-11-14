package moneyassistant.expert.view.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import moneyassistant.expert.R;
import moneyassistant.expert.viewmodel.AccountViewModel;
import moneyassistant.expert.viewmodel.adapter.AccountSpinnerAdapter;

public class ExportActivity extends AppCompatActivity {

    private AccountViewModel accountViewModel;
    private AccountSpinnerAdapter accountSpinnerAdapter;
    private Spinner account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        account = findViewById(R.id.accounts);
        accountSpinnerAdapter = new AccountSpinnerAdapter(this, new ArrayList<>());
        account.setAdapter(accountSpinnerAdapter);
        accountViewModel.getAccounts().observe(this, accounts -> {
            accountSpinnerAdapter.submitList(accounts);
        });
    }
}
