package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnCheckModelCount;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.viewmodel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity implements OnCheckModelCount {

    private TextView categoryIcon;
    private EditText categoryName;
    private Spinner categoryType;

    private String icon;
    private long id;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.setOnCheckModelCount(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        categoryType = findViewById(R.id.category_type);
        categoryName = findViewById(R.id.category_name);
        String[] walletArray = getResources().getStringArray(R.array.group_category_type);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, walletArray);
        categoryType.setAdapter(arrayAdapter);
        categoryIcon = findViewById(R.id.category_icon);
        id = getIntent().getLongExtra(Constants.resourceId, 0);
        if (id == 0) {
            setTitle(R.string.add_new_category);
            String[] icons = Constants.CATEGORY_ICONS;
            Random r = new Random();
            icon = icons[r.nextInt(icons.length - 1)];
            changeIcon();
        } else {
            categoryViewModel.getCategoryById(id).observe(this, category -> {
                if (category != null) {
                    categoryName.setText(category.getName());
                    categoryType.setSelection(category.getType().equals(Constants.EXPENSE) ? 1 : 0);
                    this.icon = category.getIcon();
                    changeIcon();
                }
            });
        }
    }

    private void changeIcon() {
        int id = getResources().getIdentifier(icon, "drawable", getPackageName());
        Drawable drawable = getDrawable(id);
        categoryIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
    }

    public void selectIcon(View view) {
        Intent intent = new Intent(this, IconsDialog.class);
        startActivityForResult(intent, Constants.CHANGE_ICON_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CHANGE_ICON_REQUEST_CODE) {
            if (resultCode == Constants.CHANGE_ICON_RESULT_CODE) {
                if (data != null) {
                    icon = data.getStringExtra("icon");
                    changeIcon();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                categoryViewModel.checkTransactions(id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void save(View view) {
        if (categoryName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_name), Toast.LENGTH_SHORT).show();
            return;
        }
        Category category = new Category();
        category.setName(categoryName.getText().toString());
        category.setType(categoryType.getSelectedItemPosition() == 1
                ? Constants.EXPENSE : Constants.INCOME);
        category.setIcon(icon);
        if (id == 0) {
            categoryViewModel.insert(category);
        } else {
            category.setId(id);
            categoryViewModel.update(category);
        }
        onBackPressed();
    }

    @Override
    public void onCheck(int count) {
        if (count > 1) {
            Util.createDialog(this, R.string.error_del);
        } else {
            categoryViewModel.delete(id);
            onBackPressed();
        }
    }
}
