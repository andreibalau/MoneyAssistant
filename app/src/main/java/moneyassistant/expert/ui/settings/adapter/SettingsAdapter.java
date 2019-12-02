package moneyassistant.expert.ui.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import moneyassistant.expert.R;
import moneyassistant.expert.model.Setting;
import moneyassistant.expert.ui.settings.listener.OnSettingsClickListener;

/**
 * MoneyAssistant
 * Created by catalin on 30.11.2019
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.BaseViewHolder> {

    private List<Setting> settingList = new ArrayList<>();
    private OnSettingsClickListener listener;

    public SettingsAdapter(OnSettingsClickListener listener) {
        this.listener = listener;
    }

    public void add(List<Setting> settingList) {
        this.settingList.clear();
        this.settingList.addAll(settingList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Setting.LABEL_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_label_view, parent, false);
            return new LabelViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_view, parent, false);
            return new SettingsViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return settingList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.render(settingList.get(position), holder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return settingList.size();
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void render(Setting setting, Context context);

    }

    class SettingsViewHolder extends BaseViewHolder {

        private ImageView icon;
        private TextView title;

        SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            itemView.findViewById(R.id.item).setOnClickListener(v -> onSettingClick());
        }

        @Override
        void render(Setting setting, Context context) {
            this.icon.setImageResource(setting.getIcon());
            this.title.setText(context.getString(setting.getTitle()));
        }

        private void onSettingClick() {
            listener.onClick(settingList.get(getAdapterPosition()));
        }

    }

    class LabelViewHolder extends BaseViewHolder {

        private TextView label;

        LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
        }

        @Override
        void render(Setting setting, Context context) {
            this.label.setText(context.getString(setting.getTitle()));
        }

    }

}
