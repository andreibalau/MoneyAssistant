package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnCheckModelCount;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.util.RecyclerItemTouchHelper;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.viewmodel.CategoryViewModel;
import moneyassistant.expert.viewmodel.adapter.CategoryAdapter;

public class CategoriesActivity extends AppCompatActivity implements
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
        OnItemClickListener, OnCheckModelCount {

    private CategoryAdapter categoryAdapter;
    private CoordinatorLayout coordinatorLayout;
    private CategoryViewModel categoryViewModel;
    private int deletedIndex;
    private Category deletedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.setOnCheckModelCount(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        String categoryType = getIntent().getStringExtra(Constants.CATEGORY);
        toolbar.setTitle(categoryType);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        coordinatorLayout = findViewById(R.id.coordinator);
        String type = Category.CategoryTypes.Income;
        if (categoryType.equals(getString(R.string.expense_category))) {
            type = Category.CategoryTypes.Expense;
        }
        RecyclerView recyclerView = findViewById(R.id.recycler);
        categoryAdapter = new CategoryAdapter(this, R.layout.category_view);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerItemTouchHelper simpleCallback = new
                RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        simpleCallback.setCategoryAdapter(categoryAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        categoryViewModel.getCategories(type).observe(this,
                categories -> categoryAdapter.submitList(categories));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, CategoryActivity.class);
                intent.putExtra(Constants.resourceId, 0);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CategoryAdapter.CategoryViewHolder) {
            deletedIndex = viewHolder.getAdapterPosition();
            deletedItem = categoryAdapter.getCategoryAt(deletedIndex);
            categoryViewModel.checkTransactions(deletedItem.getId());
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(Constants.resourceId, categoryAdapter.getCategoryAt(position).getId());
        startActivity(intent);
    }

    @Override
    public void onCheck(int count) {
        if (count > 1) {
            Util.createDialog(this, R.string.error_del);
            categoryAdapter.notifyItemChanged(deletedIndex);
        } else {
            categoryViewModel.delete(deletedItem);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, deletedItem.getName() + " " +
                            getString(R.string.deleted), Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", view -> categoryViewModel.insert(deletedItem));
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
