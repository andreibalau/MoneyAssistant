package moneyassistant.expert.old.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import moneyassistant.expert.R;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    public static void launchJob(Context context, Class<?> cls) {
        ComponentName serviceComponent = new ComponentName(context, cls);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        long millis = 1000 * 60 * 60 * 20;
        builder.setMinimumLatency(millis);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        if (jobScheduler != null) jobScheduler.schedule(builder.build());
    }

    public static void stopJob(Context context) {
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        if (jobScheduler != null) jobScheduler.cancelAll();
    }

    public static void startAlarmManager(Context context, Calendar calendar, Class cls) {
        Intent alarmIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public static void stopAlarmManager(Context context, Class cls) {
        Intent alarmIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(pendingIntent);
        }
    }

    public static void customAnimation(Context context, View view, int duration, int anim) {
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void createDialogWithButtons(Context context, String message,
                                               DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, onClickListener);
        builder.setNegativeButton(R.string.no, null);
        builder.create().show();
    }

    public static void createDialogWithButtons(Context context, int message,
                                               DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, onClickListener);
        builder.setNegativeButton(R.string.no, null);
        builder.create().show();
    }

    public static void createDialog(Context context, String message,
                                    DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, clickListener);
        builder.create().show();
    }

    public static void createDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public static void createDialog(Context context, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public static void createDialog(Context context, int message,
                                    DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, clickListener);
        builder.create().show();
    }

    public static void createListDialog(Context context, int options,
                                        String title, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, clickListener);
        builder.create().show();
    }

    public static void createListDialog(Context context, int options,
                                        int title, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, clickListener);
        builder.create().show();
    }

    public static void putInSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return Objects.requireNonNull(cm).getActiveNetworkInfo() != null;
    }

    public static Date stringToDate(String date, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.parse(date);
    }

    public static String dateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static void loadLocale(Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language, context);
    }

    public static String getLanguage(Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        return prefs.getString(langPref, "");
    }

    public static void changeLang(String lang, Context context) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang, context);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        ((AppCompatActivity) context).getBaseContext().getResources()
                .updateConfiguration(config, ((AppCompatActivity) context)
                        .getBaseContext().getResources().getDisplayMetrics());

    }

    private static void saveLocale(String lang, Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(150);
        int g = random.nextInt(150);
        int b = random.nextInt(150);
        return Color.argb(255, r, g, b);
    }
}