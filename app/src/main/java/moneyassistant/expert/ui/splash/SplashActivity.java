package moneyassistant.expert.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.old.util.Constants;
import moneyassistant.expert.ui.intro.IntroActivity;
import moneyassistant.expert.ui.main.MainActivity;
import moneyassistant.expert.util.IntelViewModelFactory;

public class SplashActivity extends AppCompatActivity {

    @Inject
    IntelViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MoneyAssistant.getAppComponent().inject(this);
        SplashViewModel viewModel = new ViewModelProvider(this, factory).get(SplashViewModel.class);
        viewModel.getIsFirstTime().observe(this, this::checkFirstTime);
    }

    private void checkFirstTime(boolean isFirstTime) {
        new Handler().postDelayed(() -> {
            if (isFirstTime) {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            finish();
        }, Constants.SPLASH_SCREEN_TIMEOUT);
    }

}
