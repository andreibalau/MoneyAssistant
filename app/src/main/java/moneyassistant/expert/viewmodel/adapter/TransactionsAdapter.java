package moneyassistant.expert.viewmodel.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.util.Util;

public class TransactionsAdapter extends ListAdapter<TransactionWithCA,
        TransactionsAdapter.TransactionsViewHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;
    private String currency;

    public TransactionsAdapter(OnItemClickListener onItemClickListener, Context context) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.currency = Util.getFromSharedPreferences(context, Constants.PREFERED_CURRENCY);
    }

    private static final DiffUtil.ItemCallback<TransactionWithCA> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TransactionWithCA>() {
                @Override
                public boolean areItemsTheSame(TransactionWithCA oldItem, TransactionWithCA newItem) {
                    Transaction t1 = oldItem.getTransaction();
                    Transaction t2 = newItem.getTransaction();
                    return t1.getId() == t2.getId();
                }

                @Override
                public boolean areContentsTheSame(TransactionWithCA oldItem, TransactionWithCA newItem) {
                    Transaction t1 = oldItem.getTransaction();
                    Transaction t2 = newItem.getTransaction();
                    return t1.getType().equals(t2.getType()) &&
                            t1.getDate().equals(t2.getDate()) &&
                            t1.getAccountId() == t2.getAccountId() &&
                            t1.getDetails().equals(t2.getDetails()) &&
                            t1.getCategoryId() == t2.getCategoryId() &&
                            t1.getAmount() == t2.getAmount();
                }
            };

    public TransactionWithCA getTransactionAt(int position) {
        return getItem(position);
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
        TransactionWithCA t = getTransactionAt(position);
        Category category = t.getCategory();
        Transaction transaction = t.getTransaction();
        int id = context.getResources().getIdentifier(category.getIcon(), "drawable", context.getPackageName());
        Drawable drawable = context.getDrawable(id);
        holder.amount.setText(String.format(Locale.getDefault(), "%.2f %s",
                transaction.getAmount(), currency));
        holder.date.setText(transaction.getDate());
        holder.categoryName.setText(category.getName());
        holder.categoryIcon.setImageDrawable(drawable);
        int color = context.getColor(R.color.colorGreen);
        if (transaction.getType().equals(Transaction.TransactionTypes.Expense)) {
            color = context.getColor(R.color.colorRed);
        }
        holder.amount.setTextColor(color);
    }

    public class TransactionsViewHolder extends RecyclerView.ViewHolder {

        private CardView transaction;
        private TextView categoryName;
        private TextView amount;
        private TextView date;
        public ImageView categoryIcon;

        TransactionsViewHolder(View itemView) {
            super(itemView);
            transaction = itemView.findViewById(R.id.transaction);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            transaction.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));
        }

    }
}
