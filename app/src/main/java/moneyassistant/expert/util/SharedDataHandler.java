package moneyassistant.expert.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
public class SharedDataHandler {

    public static final String FIRST_TIME = "first-time";
    public static final String DISPLAY_FIELD_TYPE = "display-field-type";
    public static final String DISPLAY_PATTERN_TYPE = "display-pattern-type";
    public static final String PREFERED_CURRENCY = "prefered-currency";

    private final SharedPreferences sharedPreferences;
    private static final String APP = "money";
    private Gson gson;

    public SharedDataHandler(Context context, Gson gson) {
        sharedPreferences = context.getSharedPreferences(APP, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    public void storeString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void storeObject(String key, Object object) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(object));
        editor.apply();
    }

    public void storeBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void storeInteger(String key, Integer value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public <T> T getObject(String key, String defaultValue, Class<T> cls) {
        String result = getString(key, defaultValue);
        if (result == null || result.equals(defaultValue)) return null;
        return gson.fromJson(result, cls);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

}
