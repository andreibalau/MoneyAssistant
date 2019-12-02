package moneyassistant.expert.ui.transaction.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.Currency;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.ui.transaction.fragment.BottomTransaction;
import moneyassistant.expert.ui.transaction.viewmodel.ViewTransactionViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;
import moneyassistant.expert.util.SharedDataHandler;

import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_DAY_MONTH_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.dateToString;
import static moneyassistant.expert.util.SharedDataHandler.PREFERED_CURRENCY;

public class TransactionActivity extends AppCompatActivity {

    public static final String TRANSACTION_ID = "transaction-id";

    @BindView(R.id.category_icon)
    ImageView categoryIcon;
    @BindView(R.id.category_name)
    TextView categoryName;
    @BindView(R.id.category_type)
    TextView categoryType;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.money)
    TextView amount;
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    IntelViewModelFactory factory;
    @Inject
    SharedDataHandler sharedDataHandler;
    private Unbinder unbinder;
    private ViewTransactionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        unbinder = ButterKnife.bind(this);
        MoneyAssistant.getAppComponent().inject(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        viewModel = new ViewModelProvider(this, factory).get(ViewTransactionViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long id = getIntent().getLongExtra(TRANSACTION_ID, 0);
        viewModel.fetchTransaction(id).removeObservers(this);
        viewModel.fetchTransaction(id).observe(this, this::consumeTransaction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.confirm_delete);
            builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                viewModel.delete();
                finish();
            });
            builder.setNegativeButton(R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorRed));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.edit)
    void edit() {
        TransactionWithCA transactionWithCA = viewModel.getTransaction();
        if (transactionWithCA != null) {
            BottomTransaction bottomTransaction = BottomTransaction
                    .getInstance(transactionWithCA, null);
            bottomTransaction.show(getSupportFragmentManager(), "Edit Transaction");
        }
    }

    private void consumeTransaction(TransactionWithCA transactionWithCA) {
        viewModel.setTransaction(transactionWithCA);
        if (transactionWithCA != null) {
            String currency = sharedDataHandler.getString(PREFERED_CURRENCY, Currency.RON.getValue());
            Category category = transactionWithCA.getCategory();
            Account a = transactionWithCA.getAccount();
            Transaction transaction = transactionWithCA.getTransaction();
            int resId = getResources().getIdentifier(category.getIcon(),
                    "drawable", getPackageName());
            Drawable drawable = getDrawable(resId);
            categoryIcon.setImageDrawable(drawable);
            categoryName.setText(category.getName());
            String txt = transaction.getType().substring(0, 1).toUpperCase() +
                    transaction.getType().substring(1).toLowerCase();
            categoryType.setText(txt);
            account.setText(a.getName());
            amount.setText(String.format(Locale.getDefault(),
                    "%.2f %s", transaction.getAmount(), currency));
            details.setText(transaction.getDetails());
            date.setText(dateToString(transaction.getDate(), DATE_FORMAT_DAY_MONTH_YEAR));
        }
    }

}
