package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.FragmentEvent;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.fragment.BottomTransaction;
import moneyassistant.expert.viewmodel.CategoryViewModel;
import moneyassistant.expert.viewmodel.adapter.CategoryAdapter;

public class AddTransactionActivity extends AppCompatActivity
        implements FragmentEvent, OnItemClickListener {

    private static final String TAG = "AddTransactionActivity";
    
    private CategoryAdapter categoryAdapter;
    private CategoryViewModel categoryViewModel;
    private String type;
    private TextView noContent;
    private ProgressBar progressBar;
    private int position = 1;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("");
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);
        }
        noContent = findViewById(R.id.no_content);
        progressBar = findViewById(R.id.progressbar);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item2,
                        getResources().getStringArray(R.array.group_category));
        spinnerArrayAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item2);
        spinner.setAdapter(spinnerArrayAdapter);
        categoryAdapter = new CategoryAdapter(this, R.layout.category_view_grid);
        RecyclerView categoriesRecycler = findViewById(R.id.categories_recycler);
        categoriesRecycler.setAdapter(categoryAdapter);
        categoriesRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        categoriesRecycler.setItemAnimator(new DefaultItemAnimator());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: " + i);
                progressBar.setVisibility(View.VISIBLE);
                type = i == 1 ? Transaction.TransactionTypes.Expense :
                        Transaction.TransactionTypes.Income;
                categoryViewModel.getCategories(type).removeObservers(AddTransactionActivity.this);
                categoryViewModel.getCategories(type).observe(AddTransactionActivity.this,
                        categories -> {
                            categoryAdapter.submitList(categories);
                            progressBar.setVisibility(View.GONE);
                            noContent.setVisibility(categories.isEmpty() ? View.VISIBLE : View.GONE);
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        spinner.setSelection(position);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        position = spinner.getSelectedItemPosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, CategoryActivity.class);
                intent.putExtra(Constants.resourceId, (long) 0);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp");
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
    }

    @Override
    public void onSave() {
        Log.d(TAG, "onSave");
        onBackPressed();
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick: "+position);
        Category category = categoryAdapter.getCategoryAt(position);
        TransactionWithCA transactionWithCA = new TransactionWithCA();
        transactionWithCA.setCategory(category);
        Transaction transaction = new Transaction();
        transaction.setCategoryId(category.getId());
        Calendar calendar = Calendar.getInstance();
        String date = Util.dateToString(calendar.getTime(), Constants.DATE_FORMAT_4);
        transaction.setDate(date);
        transaction.setType(type);
        transactionWithCA.setTransaction(transaction);
        BottomTransaction bottomTransaction = new BottomTransaction();
        bottomTransaction.setTransaction(transactionWithCA);
        bottomTransaction.show(getSupportFragmentManager(), "Add Transaction");
    }
}
