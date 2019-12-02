package moneyassistant.expert.ui.account.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.old.util.Constants;
import moneyassistant.expert.old.util.Util;
import moneyassistant.expert.ui.account.listener.OnAccountClickListener;

public class AccountAdapter extends ListAdapter<Account, AccountAdapter.AccountViewHolder> {

    private static final DiffUtil.ItemCallback<Account> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Account>() {
                @Override
                public boolean areItemsTheSame(Account oldItem, Account newItem) {
                    return Objects.equals(oldItem.getId(), newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Account oldItem, Account newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getType().equals(newItem.getType()) &&
                            Objects.equals(oldItem.getStartingAmount(), newItem.getStartingAmount());
                }
            };
    private OnAccountClickListener listener;

    public AccountAdapter(OnAccountClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
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
        holder.render(getItem(position));
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.account_name)
        TextView accountName;
        @BindView(R.id.account_current_value)
        TextView accountCurrentAmount;
        @BindView(R.id.item)
        LinearLayout item;

        AccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(Account account) {
            accountName.setText(account.getName());
            String currency = Util.getFromSharedPreferences(itemView.getContext(), Constants.PREFERED_CURRENCY);
            accountCurrentAmount.setText(String.format(Locale.getDefault(), "%s: %.2f %s",
                    itemView.getResources().getString(R.string.wallet_current_amount),
                    account.getCurrentAmount(), currency));
        }

        @OnClick(R.id.item)
        void onItemClick() {
            listener.onClick(getItem(getAdapterPosition()));
        }

    }
}
