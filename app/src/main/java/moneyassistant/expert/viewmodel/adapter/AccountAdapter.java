package moneyassistant.expert.viewmodel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.util.Util;

public class AccountAdapter extends ListAdapter<Account, AccountAdapter.AccountViewHolder> {

    private OnItemClickListener onItemClickListener;

    public AccountAdapter(OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
    }

    private static final DiffUtil.ItemCallback<Account> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Account>() {
        @Override
        public boolean areItemsTheSame(Account oldItem, Account newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Account oldItem, Account newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getStartingAmount() == newItem.getStartingAmount();
        }
    };

    public Account getAccountAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_view, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = getAccountAt(position);
        holder.accountName.setText(account.getName());
        String currency = Util.getFromSharedPreferences(holder.itemView.getContext(), Constants.PREFERED_CURRENCY);
        holder.accountCurrentAmount.setText(String.format(Locale.getDefault(), "%s: %.2f %s",
               holder.itemView.getResources().getString(R.string.wallet_current_amount),
                account.getCurrentAmount(), currency));
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView accountName;
        private TextView accountCurrentAmount;
        public LinearLayout viewForeground;

        AccountViewHolder(View itemView) {
            super(itemView);
            accountCurrentAmount = itemView.findViewById(R.id.account_current_value);
            accountName = itemView.findViewById(R.id.account_name);
            viewForeground = itemView.findViewById(R.id.item);
            viewForeground.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(getAdapterPosition());
        }

    }
}
