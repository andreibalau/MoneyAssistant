package moneyassistant.expert.ui.settings.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.Setting;
import moneyassistant.expert.ui.settings.adapter.SettingsAdapter;
import moneyassistant.expert.ui.settings.listener.OnSettingsClickListener;
import moneyassistant.expert.ui.settings.viewmodel.SettingsViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

/**
 * MoneyAssistant
 * Created by catalin on 17.11.2019
 */
public class SettingsFragment extends Fragment implements OnSettingsClickListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @Inject
    IntelViewModelFactory factory;

    private Unbinder unbinder;
    private SettingsAdapter settingsAdapter;
    private Context context;

    public SettingsFragment() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        MoneyAssistant.getAppComponent().inject(this);
        SettingsViewModel viewModel = new ViewModelProvider(this, factory).get(SettingsViewModel.class);
        viewModel.getSettings().observe(getViewLifecycleOwner(), this::consumeSettings);
        viewModel.setContext(context);
        settingsAdapter = new SettingsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(settingsAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Setting setting) {
        if (setting.getIntent() != null) {
            startActivity(setting.getIntent());
        } else if (setting.getDialog() != null) {
            setting.getDialog().show();
        }
    }

    private void consumeSettings(List<Setting> settingList) {
        if (settingList != null) {
            settingsAdapter.add(settingList);
        }
    }
}
