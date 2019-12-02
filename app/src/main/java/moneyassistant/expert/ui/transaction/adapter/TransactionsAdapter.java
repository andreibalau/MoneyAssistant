package moneyassistant.expert.ui.transaction.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.model.Type;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.ui.transaction.listener.OnTransactionClickListener;

import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_DAY_MONTH_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.dateToString;

public class TransactionsAdapter extends ListAdapter<TransactionWithCA,
        TransactionsAdapter.TransactionsViewHolder> {

    private static final DiffUtil.ItemCallback<TransactionWithCA> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TransactionWithCA>() {
                @Override
                public boolean areItemsTheSame(TransactionWithCA oldItem, TransactionWithCA newItem) {
                    Transaction t1 = oldItem.getTransaction();
                    Transaction t2 = newItem.getTransaction();
                    return Objects.equals(t1.getId(), t2.getId());
                }

                @Override
                public boolean areContentsTheSame(TransactionWithCA oldItem, TransactionWithCA newItem) {
                    Transaction t1 = oldItem.getTransaction();
                    Transaction t2 = newItem.getTransaction();
                    return t1.getType().equals(t2.getType()) &&
                            t1.getDate().equals(t2.getDate()) &&
                            Objects.equals(t1.getAccountId(), t2.getAccountId()) &&
                            t1.getDetails().equals(t2.getDetails()) &&
                            Objects.equals(t1.getCategoryId(), t2.getCategoryId()) &&
                            Objects.equals(t1.getAmount(), t2.getAmount());
                }
            };

    private OnTransactionClickListener listener;
    private String currency;

    public TransactionsAdapter(OnTransactionClickListener listener, String currency) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.currency = currency;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_view, parent, false);
        return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        holder.render(getItem(position), holder.itemView.getContext());
    }

    class TransactionsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_name)
        TextView categoryName;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.category_icon)
        ImageView categoryIcon;

        TransactionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(TransactionWithCA t, Context context) {
            Category category = t.getCategory();
            Transaction transaction = t.getTransaction();
            int id = context.getResources().getIdentifier(category.getIcon(), "drawable", context.getPackageName());
            Drawable drawable = context.getDrawable(id);
            amount.setText(String.format(Locale.getDefault(), "%.2f %s",
                    transaction.getAmount(), currency));
            date.setText(dateToString(transaction.getDate(), DATE_FORMAT_DAY_MONTH_YEAR));
            categoryName.setText(category.getName());
            categoryIcon.setImageDrawable(drawable);
            int color = context.getColor(R.color.colorGreen);
            if (transaction.getType().equals(Type.EXPENSE.name())) {
                color = context.getColor(R.color.colorRed);
            }
            amount.setTextColor(color);
        }

        @OnClick(R.id.transaction)
        void onClick() {
            listener.onClick(getItem(getAdapterPosition()));
        }

    }
}
