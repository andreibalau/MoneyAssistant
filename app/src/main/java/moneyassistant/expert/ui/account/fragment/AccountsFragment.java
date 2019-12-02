package moneyassistant.expert.ui.account.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.ui.account.activity.AccountActivity;
import moneyassistant.expert.ui.account.adapter.AccountAdapter;
import moneyassistant.expert.ui.account.listener.OnAccountClickListener;
import moneyassistant.expert.ui.account.viewmodel.AccountViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

import static moneyassistant.expert.ui.account.activity.AccountActivity.ACCOUNT_ID;

/**
 * MoneyAssistant
 * Created by catalin on 17.11.2019
 */
public class AccountsFragment extends Fragment implements OnAccountClickListener {

    @Inject
    IntelViewModelFactory factory;
    @BindView(R.id.no_content)
    TextView noContent;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.wallets_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Unbinder unbinder;
    private AccountAdapter accountAdapter;
    private Context context;

    public AccountsFragment() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        setHasOptionsMenu(true);
        MoneyAssistant.getAppComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);
        AccountViewModel viewModel = new ViewModelProvider(this, factory).get(AccountViewModel.class);
        accountAdapter = new AccountAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(accountAdapter);
        viewModel.findAllAccounts().observe(getViewLifecycleOwner(), this::consumeAccounts);
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(context, AccountActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Account account) {
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra(ACCOUNT_ID, account.getId());
        startActivity(intent);
    }

    private void consumeAccounts(List<Account> accountList) {
        if (accountList == null || accountList.size() == 0) {
            noContent.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
        accountAdapter.submitList(accountList);
    }

}
