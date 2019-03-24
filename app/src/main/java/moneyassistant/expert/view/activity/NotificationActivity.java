package moneyassistant.expert.view.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import moneyassistant.expert.R;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.NotificationService;
import moneyassistant.expert.util.Util;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Switch notification = findViewById(R.id.notification);
        String hasNotifications = Util.getFromSharedPreferences(this, Constants.NOTIFICATION);
        notification.setChecked(hasNotifications.equals("true"));
        notification.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener =
            (compoundButton, b) -> Util.putInSharedPreferences(NotificationActivity.this,
                    Constants.NOTIFICATION, String.valueOf(b));

    @Override
    protected void onDestroy() {
        String hasNotifications = Util.getFromSharedPreferences(this, Constants.NOTIFICATION);
        if (hasNotifications.equals("true")) {
            Util.stopJob(this);
            Util.launchJob(this, NotificationService.class);
        } else {
            Util.stopJob(this);
        }
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
