package moneyassistant.expert.ui.settings.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.R;
import moneyassistant.expert.model.Currency;
import moneyassistant.expert.model.Setting;
import moneyassistant.expert.ui.category.activity.CategoriesActivity;
import moneyassistant.expert.util.SharedDataHandler;

import static moneyassistant.expert.model.Setting.LABEL_TYPE;
import static moneyassistant.expert.model.Setting.SETTING_TYPE;
import static moneyassistant.expert.util.SharedDataHandler.PREFERED_CURRENCY;

/**
 * MoneyAssistant
 * Created by catalin on 30.11.2019
 */
public class SettingsViewModel extends ViewModel {

    private MutableLiveData<List<Setting>> settings = new MutableLiveData<>();
    private final SharedDataHandler sharedDataHandler;

    @Inject
    SettingsViewModel(SharedDataHandler sharedDataHandler) {
        this.sharedDataHandler = sharedDataHandler;
    }

    public void setContext(Context context) {
        addSettings(context);
    }

    public MutableLiveData<List<Setting>> getSettings() {
        return settings;
    }

    private void addSettings(Context context) {
        List<Setting> settingList = new ArrayList<>();
        settingList.add(new Setting(LABEL_TYPE, 0, R.string.configuration));
        settingList.add(createCategorySetting(context));
        settingList.add(createCurrencySetting(context));
        settingList.add(new Setting(LABEL_TYPE, 0, R.string.other));
        settingList.add(createAboutSetting(context));
        settingList.add(createShareSetting(context));
        settingList.add(createRatingSetting(context));
        settings.setValue(settingList);
    }

    private Setting createCategorySetting(Context context) {
        Setting setting = new Setting(SETTING_TYPE,
                R.drawable.ic_baseline_view_module_24px, R.string.categories);
        setting.setIntent(new Intent(context, CategoriesActivity.class));
        return setting;
    }

    private Setting createCurrencySetting(Context context) {
        Setting setting = new Setting(SETTING_TYPE,
                R.drawable.ic_baseline_attach_money_24px, R.string.currency);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.currency);
        builder.setItems(Currency.getItems(), (dialog, which) -> {
            CharSequence[] array = Currency.getItems();
            sharedDataHandler.storeString(PREFERED_CURRENCY, array[which].toString());
        });
        setting.setDialog(builder.create());
        return setting;
    }

    private Setting createAboutSetting(Context context) {
        Setting setting = new Setting(SETTING_TYPE,
                R.drawable.ic_baseline_info_24px, R.string.about);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(null);
        setting.setDialog(dialog);
        return setting;
    }

    private Setting createShareSetting(Context context) {
        Setting setting = new Setting(SETTING_TYPE,
                R.drawable.ic_baseline_share_24px, R.string.share);
        String packageName = context.getPackageName();
        Intent appShareIntent = new Intent(Intent.ACTION_SEND);
        appShareIntent.setType("text/plain");
        String extraText = String.format("Hy there! Try this awesome app - %s.",
                context.getResources().getString(R.string.app_name));
        extraText += "https://play.google.com/store/apps/details?id=" + packageName;
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        setting.setIntent(appShareIntent);
        return setting;
    }

    private Setting createRatingSetting(Context context) {
        Setting setting = new Setting(SETTING_TYPE,
                R.drawable.ic_baseline_star_rate_18px, R.string.rating);
        String packageName = context.getPackageName();
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
        setting.setIntent(app);
        return setting;
    }

}
