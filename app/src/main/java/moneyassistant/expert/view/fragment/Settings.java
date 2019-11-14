package moneyassistant.expert.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import moneyassistant.expert.view.activity.NotificationActivity;

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
        TextView categories = view.findViewById(R.id.categories);
        TextView currency = view.findViewById(R.id.currency);
//        TextView language = view.findViewById(R.id.language);
        TextView about = view.findViewById(R.id.about);
        TextView share = view.findViewById(R.id.share);
        TextView notifications = view.findViewById(R.id.notifications);
        TextView rate = view.findViewById(R.id.rate);
        categories.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CategoriesActivity.class);
            startActivity(intent);
        });
        currency.setOnClickListener(view15 ->
            Util.createListDialog(getActivity(), R.array.group_currency, R.string.currency,
                (dialogInterface, i) -> {
                    String[] array = getResources().getStringArray(R.array.group_currency);
                    Util.putInSharedPreferences(appCompatActivity, Constants.PREFERED_CURRENCY, array[i]);
                }));
//        language.setOnClickListener(view16 -> {
//
//        });
        about.setOnClickListener(view17 -> {
            Dialog dialog = new Dialog(appCompatActivity);
            dialog.setContentView(R.layout.about_dialog);
            dialog.setTitle(null);
            dialog.show();
        });
        share.setOnClickListener(view18 -> {
            String packageName = appCompatActivity.getPackageName();
            Intent appShareIntent = new Intent(Intent.ACTION_SEND);
            appShareIntent.setType("text/plain");
            String extraText = String.format("Hy there! Try this awesome app - %s.",
                    getResources().getString(R.string.app_name));
            extraText += "https://play.google.com/store/apps/details?id=" + packageName;
            appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            startActivity(appShareIntent);
        });
        notifications.setOnClickListener(view19 ->
                startActivity(new Intent(getActivity(), NotificationActivity.class)));
        rate.setOnClickListener(view110 -> {
            String packageName = appCompatActivity.getPackageName();
            String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
            Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
            startActivity(app);
        });
        return view;
    }
}
