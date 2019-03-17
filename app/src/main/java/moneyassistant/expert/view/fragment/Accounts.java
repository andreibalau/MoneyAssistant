package moneyassistant.expert.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnCheckModelCount;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.util.RecyclerItemTouchHelper;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.activity.AccountActivity;
import moneyassistant.expert.viewmodel.AccountViewModel;
import moneyassistant.expert.viewmodel.adapter.AccountAdapter;

public class Accounts extends Fragment
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
                OnItemClickListener, OnCheckModelCount {

    private static final String TAG = "Accounts";

    public Accounts() { }

    private AccountAdapter accountAdapter;
    private CoordinatorLayout coordinatorLayout;
    private AccountViewModel accountViewModel;
    private int deletedIndex;
    private Account deletedItem;
    private AppCompatActivity appCompatActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.accounts_fragment, container, false);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.setOnCheckModelCount(this);
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        RecyclerView recyclerView = view.findViewById(R.id.wallets_recycler);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        appCompatActivity = (AppCompatActivity) getActivity();
        toolbar.setTitle(R.string.accounts);
        accountAdapter = new AccountAdapter(this);
        recyclerView.setAdapter(accountAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        RecyclerItemTouchHelper itemTouchHelper = new
                RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        itemTouchHelper.setAccountAdapter(accountAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(appCompatActivity,
                DividerItemDecoration.VERTICAL));
        appCompatActivity.setSupportActionBar(toolbar);
        accountViewModel.getAccounts().observe(this,
                accounts -> accountAdapter.submitList(accounts));
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra(Constants.resourceId, (long) 0);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Log.d(TAG, "onSwiped");
        if (viewHolder instanceof AccountAdapter.AccountViewHolder) {
            deletedIndex = viewHolder.getAdapterPosition();
            deletedItem = accountAdapter.getAccountAt(deletedIndex);
            accountViewModel.checkTransactions(deletedItem.getId());
        }
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick: " + position);
        Intent intent = new Intent(getActivity(), AccountActivity.class);
        intent.putExtra(Constants.resourceId, accountAdapter.getAccountAt(position).getId());
        startActivity(intent);
    }

    @Override
    public void onCheck(int count) {
        Log.d(TAG, "onCheck: " + count);
        if (count > 0) {
            Util.createDialog(getActivity(), R.string.error_del);
            accountAdapter.notifyItemChanged(deletedIndex);
        } else {
            accountViewModel.delete(deletedItem);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, deletedItem.getName() + " " +
                            getString(R.string.deleted), Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", view -> accountViewModel.insert(deletedItem));
            snackbar.setActionTextColor(appCompatActivity.getColor(R.color.colorAccent));
            snackbar.show();
        }
    }
}
