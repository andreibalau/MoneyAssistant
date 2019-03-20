package moneyassistant.expert.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.FragmentEvent;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.fragment.BottomTransaction;
import moneyassistant.expert.viewmodel.AccountViewModel;
import moneyassistant.expert.viewmodel.TransactionViewModel;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class TransactionActivity extends AppCompatActivity implements FragmentEvent {

    private ImageView categoryIcon;
    private TextView categoryName;
    private TextView categoryType;
    private TextView account;
    private TextView amount;
    private TextView details;
    private TextView date;

    private TransactionWithCA transactionWithCA;

    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.details);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String currency = Util.getFromSharedPreferences(this, Constants.PREFERED_CURRENCY);
        categoryIcon = findViewById(R.id.category_icon);
        categoryName = findViewById(R.id.category_name);
        categoryType = findViewById(R.id.category_type);
        account = findViewById(R.id.account);
        amount = findViewById(R.id.money);
        details = findViewById(R.id.details);
        date = findViewById(R.id.date);
        long id = getIntent().getLongExtra(Constants.resourceId, 0);
        transactionViewModel = ViewModelProviders.of(this)
                .get(TransactionViewModel.class);
        transactionViewModel.getTransactionById(id)
                .observe(this, transactionWithCA -> {
                    if (transactionWithCA != null) {
                        this.transactionWithCA = transactionWithCA;
                        Category category = transactionWithCA.getCategory();
                        Account a = transactionWithCA.getAccount();
                        Transaction transaction = transactionWithCA.getTransaction();
                        int resId = getResources().getIdentifier(category.getIcon(),
                                "drawable", getPackageName());
                        Drawable drawable = getDrawable(resId);
                        categoryIcon.setImageDrawable(drawable);
                        categoryName.setText(category.getName());
                        String txt = transaction.getType().substring(0,1).toUpperCase() +
                                transaction.getType().substring(1).toLowerCase();
                        categoryType.setText(txt);
                        account.setText(a.getName());
                        amount.setText(String.format(Locale.getDefault(),
                                "%.2f %s", transaction.getAmount(), currency));
                        details.setText(transaction.getDetails());
                        date.setText(transaction.getDate());
                    }
        });
    }

    public void edit(View view) {
        if (transactionWithCA != null) {
            BottomTransaction bottomTransaction = new BottomTransaction();
            bottomTransaction.setTransaction(transactionWithCA);
            bottomTransaction.show(getSupportFragmentManager(), "Edit Transaction");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Util.createDialogWithButtons(this, R.string.confirm_delete,
                        (dialogInterface, i) -> {
                            transactionViewModel.delete(transactionWithCA.getTransaction());
                            finish();
                });
                break;
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
    public void onSave() {

    }
}
