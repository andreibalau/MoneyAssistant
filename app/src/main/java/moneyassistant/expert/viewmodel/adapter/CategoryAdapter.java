package moneyassistant.expert.viewmodel.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.util.OnItemClickListener;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    private static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Category>() {
                @Override
                public boolean areItemsTheSame(Category oldItem, Category newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Category oldItem, Category newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getType().equals(newItem.getType()) &&
                            oldItem.getIcon().equals(newItem.getIcon());
                }
            };

    private OnItemClickListener onItemClickListener;
    private int resource;

    public CategoryAdapter(OnItemClickListener onItemClickListener, int resource) {
        super(DIFF_CALLBACK);
        this.onItemClickListener = onItemClickListener;
        this.resource = resource;
    }

    public Category getCategoryAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getCategoryAt(position);
        Context context = holder.itemView.getContext();
        holder.categoryName.setText(category.getName());
        int id = context.getResources().getIdentifier(category.getIcon(), "drawable",
                context.getPackageName());
        Drawable drawable = context.getDrawable(id);
        holder.icon.setImageDrawable(drawable);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView categoryName;
        public LinearLayout viewForeground;
        private ImageView icon;

        CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.name);
            viewForeground = itemView.findViewById(R.id.item);
            icon = itemView.findViewById(R.id.icon);
            viewForeground.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(getAdapterPosition());
        }

    }
}
