package moneyassistant.expert.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nagivation)
    BottomNavigationView navigationView;
    private MainViewModel mainViewModel;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoneyAssistant.getAppComponent().inject(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        unbinder = ButterKnife.bind(this);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
