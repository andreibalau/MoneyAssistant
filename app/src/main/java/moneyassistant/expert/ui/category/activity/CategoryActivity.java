package moneyassistant.expert.ui.category.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.Type;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.ui.category.viewmodel.CategoryViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

import static moneyassistant.expert.ui.category.activity.IconsDialog.CHANGE_ICON_REQUEST_CODE;
import static moneyassistant.expert.ui.category.activity.IconsDialog.CHANGE_ICON_RESULT_CODE;
import static moneyassistant.expert.ui.category.activity.IconsDialog.ICON;
import static moneyassistant.expert.util.Icons.CATEGORY_ICONS;

public class CategoryActivity extends AppCompatActivity {

    public static final String CATEGORY_ID = "category_id";

    @BindView(R.id.category_icon)
    ImageView categoryIcon;
    @BindView(R.id.category_name)
    EditText categoryName;
    @BindView(R.id.category_type)
    Spinner categoryType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    IntelViewModelFactory factory;
    private Unbinder unbinder;
    private Category category;
    private CategoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        unbinder = ButterKnife.bind(this);
        MoneyAssistant.getAppComponent().inject(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        viewModel = new ViewModelProvider(this, factory).get(CategoryViewModel.class);
        long id = getIntent().getLongExtra(CATEGORY_ID, 0);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,
                getResources().getStringArray(R.array.group_category_type));
        categoryType.setAdapter(arrayAdapter);
        viewModel.findCategoryById(id).observe(this, this::consumeCategory);
    }

    @OnClick(R.id.save)
    void onSave() {
        if (categoryName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_name), Toast.LENGTH_SHORT).show();
            return;
        }
        category.setName(categoryName.getText().toString());
        category.setType(categoryType.getSelectedItemPosition() == 1
                ? Type.EXPENSE.name() : Type.INCOME.name());
        viewModel.save(category);
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.confirm_delete);
            builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                viewModel.delete(category);
                onBackPressed();
            });
            builder.setNegativeButton(R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorRed));
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.category_icon)
    void onClickCategoryIcon() {
        Intent intent = new Intent(this, IconsDialog.class);
        startActivityForResult(intent, CHANGE_ICON_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_ICON_REQUEST_CODE) {
            if (resultCode == CHANGE_ICON_RESULT_CODE) {
                if (data != null) {
                    String icon = data.getStringExtra(ICON);
                    category.setIcon(icon);
                    changeIcon();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void changeIcon() {
        int id = getResources().getIdentifier(category.getIcon(), "drawable", getPackageName());
        Drawable drawable = getDrawable(id);
        categoryIcon.setImageDrawable(drawable);
    }

    private void consumeCategory(Category category) {
        if (category != null) {
            this.category = category;
            categoryName.setText(category.getName());
            categoryType.setSelection(category.getType().equals(Type.EXPENSE.name()) ? 1 : 0);
            changeIcon();
        } else {
            this.category = new Category();
            String[] icons = CATEGORY_ICONS;
            Random r = new Random();
            String icon = icons[r.nextInt(icons.length - 1)];
            this.category.setIcon(icon);
            changeIcon();
        }
    }

}
