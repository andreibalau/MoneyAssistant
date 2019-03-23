package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import moneyassistant.expert.R;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.Util;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeSplash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String firstTime = Util.getFromSharedPreferences(this, Constants.FIRST_TIME_RUN);
        new Handler().postDelayed(() -> {
            if (firstTime.equals("") || firstTime.equals("yes")) {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            finish();
        }, Constants.SPLASH_SCREEN_TIMEOUT);
    }
}
