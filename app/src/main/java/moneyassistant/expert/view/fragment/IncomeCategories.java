package moneyassistant.expert.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import moneyassistant.expert.view.activity.CategoryActivity;
import moneyassistant.expert.viewmodel.CategoryViewModel;
import moneyassistant.expert.viewmodel.adapter.CategoryAdapter;

public class IncomeCategories extends Fragment
        implements OnItemClickListener,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, OnCheckModelCount {

    private CategoryAdapter categoryAdapter;
    private CoordinatorLayout coordinatorLayout;
    private CategoryViewModel categoryViewModel;
    private int deletedIndex;
    private Category deletedItem;
    private AppCompatActivity appCompatActivity;
    private TextView noContent;
    private ProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incomes, container, false);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.setOnCheckModelCount(this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        noContent = view.findViewById(R.id.no_content);
        progressBar = view.findViewById(R.id.progressbar);
        categoryAdapter = new CategoryAdapter(this, R.layout.category_view);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecyclerItemTouchHelper simpleCallback = new
                RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        simpleCallback.setCategoryAdapter(categoryAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        categoryViewModel.getCategories(Category.CategoryTypes.Income)
                .observe(getViewLifecycleOwner(), categories -> {
                    categoryAdapter.submitList(categories);
                    progressBar.setVisibility(View.GONE);
                    noContent.setVisibility(categories.isEmpty() ? View.VISIBLE : View.GONE);
                });
        return view;
    }

    @Override
    public void onCheck(int count) {
        if (count > 1) {
            Util.createDialog(appCompatActivity, R.string.error_del);
            categoryAdapter.notifyItemChanged(deletedIndex);
        } else {
            categoryViewModel.delete(deletedItem);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, deletedItem.getName() + " " +
                            getString(R.string.deleted), Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", view -> categoryViewModel.insert(deletedItem));
            snackbar.setActionTextColor(appCompatActivity.getColor(R.color.colorWhite));
            snackbar.show();
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(appCompatActivity, CategoryActivity.class);
        intent.putExtra(Constants.resourceId, categoryAdapter.getCategoryAt(position).getId());
        startActivity(intent);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CategoryAdapter.CategoryViewHolder) {
            deletedIndex = viewHolder.getAdapterPosition();
            deletedItem = categoryAdapter.getCategoryAt(deletedIndex);
            categoryViewModel.checkTransactions(deletedItem.getId());
        }
    }
}
