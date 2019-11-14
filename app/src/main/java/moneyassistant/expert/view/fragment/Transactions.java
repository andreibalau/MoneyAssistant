package moneyassistant.expert.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.activity.AddTransactionActivity;
import moneyassistant.expert.view.activity.ExportActivity;
import moneyassistant.expert.view.activity.TransactionActivity;
import moneyassistant.expert.viewmodel.TransactionViewModel;
import moneyassistant.expert.viewmodel.adapter.TransactionsAdapter;

public class Transactions extends Fragment implements OnItemClickListener {

    private static final String TAG = "Transactions";

    public Transactions() { }

    private TransactionsAdapter transactionsAdapter;

    private TextView totalValue;
    private TextView incomeValue;
    private TextView expenseValue;
    private FloatingActionButton addTransactionButton;
    private TextView noContent;
    private TextView indicator;
    private Calendar calendar;
    private TransactionViewModel transactionViewModel;
    private int position;

    private Date start;
    private Date end;

    private AppCompatActivity appCompatActivity;

    private ProgressBar progressBar;
    private RecyclerView transactionsRecycler;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            Log.d(TAG, "onScrollStateChanged " + newState);
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                addTransactionButton.show();
            } else {
                addTransactionButton.hide();
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            Log.d(TAG, "onScrolled");
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        calendar = Calendar.getInstance();
        View view = inflater.inflate(R.layout.transactions_fragment, container, false);
        setHasOptionsMenu(true);
        changeMillis();
        appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        noContent = view.findViewById(R.id.no_content);
        addTransactionButton = view.findViewById(R.id.add_transaction_button);
        ImageButton arrowLeft = view.findViewById(R.id.arrow_left);
        ImageButton arrowRight = view.findViewById(R.id.arrow_right);
        totalValue = view.findViewById(R.id.total_value);
        incomeValue = view.findViewById(R.id.income_value);
        expenseValue = view.findViewById(R.id.expenses_value);
        transactionsRecycler = view.findViewById(R.id.transactions_recycler);
        progressBar = view.findViewById(R.id.progressbar);
        indicator = view.findViewById(R.id.indicator);
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
        }
        transactionsAdapter = new TransactionsAdapter(this, appCompatActivity);
        transactionsRecycler.setAdapter(transactionsAdapter);
        transactionsRecycler.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        transactionsRecycler.setHasFixedSize(true);
        transactionsRecycler.addOnScrollListener(onScrollListener);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        arrowLeft.setOnClickListener(view1 -> {
            Log.d(TAG, "onClick: arrow-left");
            changeDate(-1);
        });
        arrowRight.setOnClickListener(view12 -> {
            Log.d(TAG, "onClick: arrow-right");
            changeDate(1);
        });
        addTransactionButton.setOnClickListener(view13 -> {
            Log.d(TAG, "onClick: add");
            Intent intent = new Intent(appCompatActivity, AddTransactionActivity.class);
            startActivity(intent);
            if (appCompatActivity != null) {
                appCompatActivity.overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
            }
        });
        indicator.setOnClickListener(view14 -> Util.createListDialog(getActivity(),
            R.array.group_date, getString(R.string.display_type),
                (dialogInterface, i) -> {
                    position = i;
                    changeDate(0);
                }));
        position = 1;
        changeDate(0);
        return view;
    }

    private void changeMillis() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(calendar.getTimeInMillis());
        int minmDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        int maxmDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int minyDay = c.getActualMinimum(Calendar.DAY_OF_YEAR);
        int maxyDay = c.getActualMaximum(Calendar.DAY_OF_YEAR);
        switch (position) {
            case 0:
                start = c.getTime();
                end = c.getTime();
                break;
            case 1:
                c.set(Calendar.DAY_OF_MONTH, minmDay);
                start = c.getTime();
                c.set(Calendar.DAY_OF_MONTH, maxmDay);
                end = c.getTime();
                break;
            case 2:
                c.set(Calendar.DAY_OF_YEAR, minyDay);
                start = c.getTime();
                c.set(Calendar.DAY_OF_YEAR, maxyDay);
                end = c.getTime();
                break;
        }
    }

    private void changeDate(int value) {
        progressBar.setVisibility(View.VISIBLE);
//        transactionsRecycler.setVisibility(View.GONE);
        switch (position) {
            case 0:
                calendar.add(Calendar.DAY_OF_YEAR, value);
                break;
            case 1:
                calendar.add(Calendar.MONTH, value);
                break;
            case 2:
                calendar.add(Calendar.YEAR, value);
                break;
        }
        transactionViewModel.getTransactions(start, end).removeObservers(this);
        changeMillis();
        transactionViewModel.getTransactions(start, end).observe(this, this::setDisplay);
    }

    private void setDisplay(List<TransactionWithCA> transactions) {
        String pattern = Constants.DATE_FORMAT_4;
        switch (position) {
            case 1:
                pattern = Constants.DATE_FORMAT_5;
                break;
            case 2:
                pattern = Constants.DATE_FORMAT_3;
                break;
        }
        indicator.setText(Util.dateToString(calendar.getTime(), pattern));
        double income = 0, expense = 0, total;
        for (TransactionWithCA t : transactions) {
            Transaction transaction = t.getTransaction();
            if (transaction.getType().equals(Transaction.TransactionTypes.Expense)) {
                expense += transaction.getAmount();
            } else if (transaction.getType().equals(Transaction.TransactionTypes.Income)) {
                income += transaction.getAmount();
            }
        }
        total = income - expense;
        totalValue.setText(String.format(Locale.getDefault(), "%.2f", total));
        incomeValue.setText(String.format(Locale.getDefault(), "%.2f", income));
        expenseValue.setText(String.format(Locale.getDefault(), "%.2f", expense));
        transactionsAdapter.submitList(transactions);
        progressBar.setVisibility(View.GONE);
        transactionsRecycler.setVisibility(View.VISIBLE);
        noContent.setVisibility(transactions.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick: " + position);
        TransactionsAdapter.TransactionsViewHolder viewHolder =
                (TransactionsAdapter.TransactionsViewHolder) transactionsRecycler
                        .findViewHolderForAdapterPosition(position);
        TransactionWithCA transaction = transactionsAdapter.getTransactionAt(position);
        Intent intent = new Intent(getActivity(), TransactionActivity.class);
        intent.putExtra(Constants.resourceId, transaction.getTransaction().getId());
        Bundle bundle = new Bundle();
        if (viewHolder != null) {
            ActivityOptionsCompat compat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(appCompatActivity, viewHolder.categoryIcon,
                            "transaction");
            bundle = compat.toBundle();
        }
        startActivity(intent, bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.transactions_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.export:
                startActivity(new Intent(appCompatActivity, ExportActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
