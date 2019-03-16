package moneyassistant.expert.viewmodel.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.util.OnItemClickListener;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    private List<String> iconList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public IconAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void submitList(List<String> iconList) {
        this.iconList = iconList;
        notifyDataSetChanged();
    }

    public String getIconAt(int position) {
        return this.iconList.get(position);
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
        String icon = getIconAt(position);
        Context context = holder.itemView.getContext();
        int id = context.getResources().getIdentifier(icon, "drawable",
                context.getPackageName());
        Drawable drawable = context.getDrawable(id);
        holder.icon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    class IconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon;

        IconViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(getAdapterPosition());
        }
    }
}
