package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.viewmodel.AccountViewModel;
import moneyassistant.expert.viewmodel.adapter.AccountAdapter;

public class AccountsDialog extends AppCompatActivity implements OnItemClickListener {

    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_dialog);
        setTitle(R.string.choose_account);
        AccountViewModel accountViewModel = ViewModelProviders.of(this)
                .get(AccountViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        accountAdapter = new AccountAdapter(this);
        recyclerView.setAdapter(accountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        accountViewModel.getAccounts().observe(this, accounts -> {
            accountAdapter.submitList(accounts);
        });
    }

    @Override
    public void onClick(int position) {
        Account account = accountAdapter.getAccountAt(position);
        Intent intent = new Intent();
        intent.putExtra("name", account.getName());
        intent.putExtra("id", account.getId());
        setResult(1, intent);
        onBackPressed();
    }
}
