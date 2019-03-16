package moneyassistant.expert.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import moneyassistant.expert.R;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.fragment.Accounts;
import moneyassistant.expert.view.fragment.Settings;
import moneyassistant.expert.view.fragment.Transactions;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private boolean onBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nagivation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            Util.changeFragment(this, new Transactions());
        }
    }

    @Override
    public void onBackPressed() {
        if (onBackPressed) {
            super.onBackPressed();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.press_again), Toast.LENGTH_SHORT).show();
            onBackPressed = true;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.transactions:
                fragment = new Transactions();
                item.setChecked(true);
                break;
//            case R.id.reports:
//                fragment = new Reports();
//                item.setChecked(true);
//                break;
            case R.id.accounts:
                fragment = new Accounts();
                item.setChecked(true);
                break;
            case R.id.settings:
                fragment = new Settings();
                item.setChecked(true);
                break;
        }
        if (fragment != null) {
            Util.changeFragment(this, fragment);
        }
        onBackPressed = false;
        return true;
    }
}
