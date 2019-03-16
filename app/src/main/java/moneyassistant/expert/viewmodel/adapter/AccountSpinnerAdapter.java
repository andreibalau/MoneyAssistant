package moneyassistant.expert.viewmodel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;

public class AccountSpinnerAdapter extends ArrayAdapter<Account> {

    private Context context;
    private List<Account> accountList;

    public AccountSpinnerAdapter(@NonNull Context context, List<Account> accounts) {
        super(context, 0, accounts);
        this.context = context;
        this.accountList = accounts;
    }

    public void submitList(List<Account> accountList) {
        this.accountList.addAll(accountList);
        notifyDataSetChanged();
    }

    public Account getAccountAt(int position) {
        return this.accountList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context)
                    .inflate(R.layout.account_spinner_item, parent, false);
        }
        Account account = accountList.get(position);
        TextView textView = listItem.findViewById(R.id.account);
        textView.setText(account.getName());
        return listItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context)
                    .inflate(R.layout.account_spinner_item, parent, false);
        }
        Account account = accountList.get(position);
        TextView textView = listItem.findViewById(R.id.account);
        textView.setText(account.getName());
        return listItem;
    }
}
