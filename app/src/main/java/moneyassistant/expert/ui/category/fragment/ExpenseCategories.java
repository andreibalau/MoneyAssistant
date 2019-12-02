package moneyassistant.expert.ui.category.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.Type;
import moneyassistant.expert.ui.category.activity.CategoryActivity;
import moneyassistant.expert.ui.category.adapter.CategoryAdapter;
import moneyassistant.expert.ui.category.listener.OnCategoryClickListener;
import moneyassistant.expert.ui.category.viewmodel.CategoryViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

import static moneyassistant.expert.ui.category.activity.CategoryActivity.CATEGORY_ID;

public class ExpenseCategories extends Fragment implements OnCategoryClickListener {

    @BindView(R.id.no_content)
    TextView noContent;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @Inject
    IntelViewModelFactory factory;
    private Unbinder unbinder;
    private CategoryAdapter categoryAdapter;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        unbinder = ButterKnife.bind(this, view);
        MoneyAssistant.getAppComponent().inject(this);
        CategoryViewModel viewModel = new ViewModelProvider(this, factory)
                .get(CategoryViewModel.class);
        categoryAdapter = new CategoryAdapter(this, R.layout.category_view);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        viewModel.findAllCategoriesByType(Type.EXPENSE)
                .observe(getViewLifecycleOwner(), categories -> {
                    categoryAdapter.submitList(categories);
                    progressBar.setVisibility(View.GONE);
                    noContent.setVisibility(categories.isEmpty() ? View.VISIBLE : View.GONE);
                });
        return view;
    }

    @Override
    public void onClick(Category category) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(CATEGORY_ID, category.getId());
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

}
