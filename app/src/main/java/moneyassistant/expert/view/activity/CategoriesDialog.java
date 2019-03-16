package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.viewmodel.CategoryViewModel;
import moneyassistant.expert.viewmodel.adapter.CategoryAdapter;

public class CategoriesDialog extends AppCompatActivity implements OnItemClickListener {

    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_dialog);
        setTitle(R.string.choose_category);
        String type = getIntent().getStringExtra(Constants.CATEGORY);
//        categoryAdapter = new CategoryAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        CategoryViewModel categoryViewModel = ViewModelProviders.of(this)
                .get(CategoryViewModel.class);
        categoryViewModel.getCategories(type).observe(this, categories -> {
           categoryAdapter.submitList(categories);
        });
    }

    @Override
    public void onClick(int position) {
        Category category = categoryAdapter.getCategoryAt(position);
        Intent intent = new Intent();
        intent.putExtra("name", category.getName());
        intent.putExtra("id", category.getId());
        setResult(Constants.CHANGE_CATEGORY_RESULT_CODE, intent);
        onBackPressed();
    }
}
