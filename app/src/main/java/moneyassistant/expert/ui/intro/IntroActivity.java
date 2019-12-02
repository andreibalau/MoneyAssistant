package moneyassistant.expert.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.ui.main.MainActivity;
import moneyassistant.expert.util.SharedDataHandler;

import static moneyassistant.expert.util.SharedDataHandler.FIRST_TIME;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.intro_btn_skip)
    Button skip;
    @BindView(R.id.intro_btn_finish)
    Button finish;
    @BindView(R.id.intro_btn_next)
    ImageButton next;
    @BindView(R.id.intro_indicator_0)
    ImageView indicator0;
    @BindView(R.id.intro_indicator_1)
    ImageView indicator1;
    @BindView(R.id.intro_indicator_2)
    ImageView indicator2;
    @Inject
    SharedDataHandler sharedDataHandler;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        unbinder = ButterKnife.bind(this);
        MoneyAssistant.getAppComponent().inject(this);
        sharedDataHandler.storeBoolean(FIRST_TIME, false);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new IntroFragment1());
        mSectionsPagerAdapter.addFragment(new IntroFragment2());
        mSectionsPagerAdapter.addFragment(new IntroFragment3());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

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

    @OnClick(R.id.intro_btn_skip)
    void onSkip() {
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.intro_btn_finish)
    void onFinish() {
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.intro_btn_next)
    void onNext() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
