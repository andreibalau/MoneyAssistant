package moneyassistant.expert;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import moneyassistant.expert.injection.AppComponent;
import moneyassistant.expert.injection.AppModule;
import moneyassistant.expert.injection.DaggerAppComponent;

public class MoneyAssistant extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "money-assistant-notification-channel";
    private static final String APP_NAME = "Money Assistant";
    private static final String APP_DESCRIPTION = "Money Assistant - Track your transactions";
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel(getApplicationContext());
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, APP_NAME, importance);
            channel.setDescription(APP_DESCRIPTION);
            channel.setSound(null, null);
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

}
