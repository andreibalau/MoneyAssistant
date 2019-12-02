package moneyassistant.expert.ui.category.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moneyassistant.expert.R;
import moneyassistant.expert.ui.category.listener.OnIconClickListener;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    private List<String> iconList = new ArrayList<>();
    private OnIconClickListener listener;

    public IconAdapter(OnIconClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<String> iconList) {
        this.iconList = iconList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icon_dialog_view, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        holder.render(iconList.get(position));
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    class IconViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;

        IconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(String icon) {
            Context context = itemView.getContext();
            int id = context.getResources().getIdentifier(icon, "drawable",
                    context.getPackageName());
            Drawable drawable = context.getDrawable(id);
            this.icon.setImageDrawable(drawable);
        }

        @OnClick(R.id.icon)
        void onClick() {
            listener.onClick(iconList.get(getAdapterPosition()));
        }

    }
}
