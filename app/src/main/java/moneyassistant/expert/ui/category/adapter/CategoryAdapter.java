package moneyassistant.expert.ui.category.adapter;

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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.ui.category.listener.OnCategoryClickListener;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    private static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Category>() {
                @Override
                public boolean areItemsTheSame(Category oldItem, Category newItem) {
                    return Objects.equals(oldItem.getId(), newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Category oldItem, Category newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getType().equals(newItem.getType()) &&
                            oldItem.getIcon().equals(newItem.getIcon());
                }
            };

    private OnCategoryClickListener listener;
    private int resource;

    public CategoryAdapter(OnCategoryClickListener listener, int resource) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.resource = resource;
    }

    public Category get(int position) {
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
        holder.render(getItem(position));
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView categoryName;
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.item)
        LinearLayout item;

        CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(Category category) {
            Context context = itemView.getContext();
            categoryName.setText(category.getName());
            int id = context.getResources().getIdentifier(category.getIcon(), "drawable",
                    context.getPackageName());
            Drawable drawable = context.getDrawable(id);
            icon.setImageDrawable(drawable);
        }

        @OnClick(R.id.item)
        void onClick() {
            listener.onClick(getItem(getAdapterPosition()));
        }

    }
}
