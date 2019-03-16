package moneyassistant.expert.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import moneyassistant.expert.R;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.activity.CategoriesActivity;

public class Settings extends Fragment {

    public Settings() { }

    private AppCompatActivity appCompatActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.settings);
        appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
        }
        TextView income = view.findViewById(R.id.income_categories);
        TextView expense = view.findViewById(R.id.expense_categories);
        TextView fingerprint = view.findViewById(R.id.fingerprint);
        TextView passcode = view.findViewById(R.id.passcode);
        TextView currency = view.findViewById(R.id.currency);
        TextView language = view.findViewById(R.id.language);
        TextView about = view.findViewById(R.id.about);
        TextView share = view.findViewById(R.id.share);
        TextView feedback = view.findViewById(R.id.feedback);
        TextView rate = view.findViewById(R.id.rating);
        income.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CategoriesActivity.class);
            intent.putExtra(Constants.CATEGORY, getString(R.string.income_category));
            startActivity(intent);
        });
        expense.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), CategoriesActivity.class);
            intent.putExtra(Constants.CATEGORY, getString(R.string.expense_category));
            startActivity(intent);
        });
        fingerprint.setOnClickListener(view13 -> {

        });
        passcode.setOnClickListener(view14 -> {

        });
        currency.setOnClickListener(view15 ->
                Util.createListDialog(getActivity(), R.array.group_currency, R.string.currency,
                    (dialogInterface, i) -> {
                        String[] array = getResources().getStringArray(R.array.group_currency);
                        Util.putInSharedPreferences(appCompatActivity, Constants.PREFERED_CURRENCY, array[i]);
                    }));
        language.setOnClickListener(view16 -> {

        });
        about.setOnClickListener(view17 -> {

        });
        share.setOnClickListener(view18 -> {

        });
        feedback.setOnClickListener(view19 -> {

        });
        rate.setOnClickListener(view110 -> {

        });
        return view;
    }
}
