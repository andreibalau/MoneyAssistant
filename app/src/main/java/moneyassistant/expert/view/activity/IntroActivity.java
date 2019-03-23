package moneyassistant.expert.view.activity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moneyassistant.expert.R;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.fragment.IntroFragment1;
import moneyassistant.expert.view.fragment.IntroFragment2;
import moneyassistant.expert.view.fragment.IntroFragment3;
import moneyassistant.expert.viewmodel.adapter.SectionsPagerAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Util.putInSharedPreferences(this, Constants.NOTIFICATION, "yes");
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new IntroFragment1());
        mSectionsPagerAdapter.addFragment(new IntroFragment2());
        mSectionsPagerAdapter.addFragment(new IntroFragment3());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ImageView indicator0 = findViewById(R.id.intro_indicator_0);
        ImageView indicator1 = findViewById(R.id.intro_indicator_1);
        ImageView indicator2 = findViewById(R.id.intro_indicator_2);
        Button skip = findViewById(R.id.intro_btn_skip);
        Button finish = findViewById(R.id.intro_btn_finish);
        ImageButton next = findViewById(R.id.intro_btn_next);
        next.setOnClickListener(view -> {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        });
        finish.setOnClickListener(view -> {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        });
        skip.setOnClickListener(view -> {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        indicator0.setBackgroundResource(R.drawable.indicator_selected);
                        indicator1.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator2.setBackgroundResource(R.drawable.indicator_unselected);
                        break;
                    case 1:
                        indicator0.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator1.setBackgroundResource(R.drawable.indicator_selected);
                        indicator2.setBackgroundResource(R.drawable.indicator_unselected);
                        break;
                    case 2:
                        indicator0.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator1.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator2.setBackgroundResource(R.drawable.indicator_selected);
                        break;
                }
                next.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                finish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                skip.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
