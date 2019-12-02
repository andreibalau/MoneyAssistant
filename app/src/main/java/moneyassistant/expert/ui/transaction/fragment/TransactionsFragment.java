package moneyassistant.expert.ui.transaction.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.Currency;
import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.model.Type;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.ui.transaction.activity.AddTransactionActivity;
import moneyassistant.expert.ui.transaction.activity.TransactionActivity;
import moneyassistant.expert.ui.transaction.adapter.TransactionsAdapter;
import moneyassistant.expert.ui.transaction.listener.OnTransactionClickListener;
import moneyassistant.expert.ui.transaction.viewmodel.TransactionViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;
import moneyassistant.expert.util.SharedDataHandler;

import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_DAY_MONTH_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_MONTH_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.dateToString;
import static moneyassistant.expert.util.SharedDataHandler.PREFERED_CURRENCY;

/**
 * MoneyAssistant
 * Created by catalin on 16.11.2019
 */
public class TransactionsFragment extends Fragment implements OnTransactionClickListener {

    @BindView(R.id.total_value)
    TextView totalValue;
    @BindView(R.id.income_value)
    TextView incomeValue;
    @BindView(R.id.expenses_value)
    TextView expenseValue;
    @BindView(R.id.indicator)
    TextView indicator;
    @BindView(R.id.no_content)
    TextView noContent;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.transactions_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.add_transaction)
    FloatingActionButton add;
    @BindView(R.id.divider)
    View divider;
    @Inject
    IntelViewModelFactory factory;
    @Inject
    SharedDataHandler sharedDataHandler;

    private TransactionViewModel viewModel;
    private Unbinder unbinder;
    private Context context;
    private TransactionsAdapter adapter;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                add.show();
            } else {
                add.hide();
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    public TransactionsFragment() { }

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
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        unbinder = ButterKnife.bind(this, view);
        MoneyAssistant.getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, factory).get(TransactionViewModel.class);
        viewModel.getCalendar().observe(getViewLifecycleOwner(), this::consumeCalendar);
        viewModel.getTransactions().observe(getViewLifecycleOwner(), this::consumeTransactions);
        adapter = new TransactionsAdapter(this, sharedDataHandler.getString(PREFERED_CURRENCY, Currency.RON.getValue()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnScrollListener(onScrollListener);
        return view;
    }

    @OnClick(R.id.arrow_right)
    void onClickNext() {
        viewModel.next();
    }

    @OnClick(R.id.arrow_left)
    void onClickPrev() {
        viewModel.prev();
    }

    @OnClick(R.id.add_transaction)
    void onClickAddTransaction() {
        Intent intent = new Intent(context, AddTransactionActivity.class);
        startActivity(intent);
        ((AppCompatActivity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
    }

    @OnClick(R.id.indicator)
    void onClickIndicator() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.display_type);
        builder.setItems(R.array.group_date, (dialog, which) -> {
            switch (which) {
                case 0:
                    viewModel.setupDate(DATE_FORMAT_DAY_MONTH_YEAR, Calendar.DAY_OF_YEAR);
                    break;
                case 1:
                    viewModel.setupDate(DATE_FORMAT_MONTH_YEAR, Calendar.MONTH);
                    break;
                case 2:
                    viewModel.setupDate(DATE_FORMAT_YEAR, Calendar.YEAR);
                    break;
            }
        });
        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(TransactionWithCA transactionWithCA) {
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra(TransactionActivity.TRANSACTION_ID, transactionWithCA.getTransaction().getId());
        startActivity(intent);
    }

    private void consumeTransactions(List<TransactionWithCA> transactions) {
        adapter.submitList(transactions);
        progressBar.setVisibility(View.GONE);
        noContent.setVisibility((transactions == null || transactions.isEmpty()) ? View.VISIBLE : View.GONE);

        if (transactions == null) {
            return;
        }
        double income = 0, expense = 0, total;
        for (TransactionWithCA t : transactions) {
            Transaction transaction = t.getTransaction();
            if (transaction.getType().equals(Type.EXPENSE.name())) {
                expense += transaction.getAmount();
            } else if (transaction.getType().equals(Type.INCOME.name())) {
                income += transaction.getAmount();
            }
        }
        total = income - expense;
        totalValue.setText(String.format(Locale.getDefault(), "%.2f", total));
        incomeValue.setText(String.format(Locale.getDefault(), "%.2f", income));
        expenseValue.setText(String.format(Locale.getDefault(), "%.2f", expense));
    }

    private void consumeCalendar(Calendar calendar) {
        if (calendar != null) {
            indicator.setText(dateToString(calendar, viewModel.getPattern()));
        }
    }
}
