package moneyassistant.expert.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.view.activity.DateRangeDialog;
import moneyassistant.expert.viewmodel.TransactionViewModel;
import moneyassistant.expert.viewmodel.adapter.TabAdapter;

public class Reports extends Fragment {

    public Reports() { }

    private static final String TAG = "Reports";
    public static final String ACTION_DATE_RANGE = "action_date_range";
    private AppCompatActivity appCompatActivity;
    private TextView dateRange;
    private long start;
    private long end;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
        dateRange = view.findViewById(R.id.date_range);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new ExpenseReports(), "Expense");
        tabAdapter.addFragment(new IncomeReports(), "Income");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        initialRequest();
        dateRange.setOnClickListener((v) -> {
            Intent intent = new Intent(appCompatActivity, DateRangeDialog.class);
            intent.putExtra("start", this.start);
            intent.putExtra("end", this.end);
            startActivityForResult(intent, Constants.DATE_RANGE_REQUEST_CODE);
        });
        return view;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    private void initialRequest() {
        Calendar calendar = Calendar.getInstance();
        int dayStart = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int dayEnd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String month = Util.dateToString(calendar.getTime(), Constants.DATE_FORMAT_5);
        calendar.set(Calendar.DAY_OF_MONTH, dayStart);
        Date start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, dayEnd);
        Date end = calendar.getTime();
        this.start = start.getTime();
        this.end = end.getTime();
        dateRange.setText(String.format("%s-%s %s", dayStart, dayEnd, month));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult");
        if (requestCode == Constants.DATE_RANGE_REQUEST_CODE) {
            if (resultCode == Constants.DATE_RANGE_RESULT_CODE) {
                if (data != null) {
                    this.start = data.getLongExtra("start", 0);
                    this.end = data.getLongExtra("end", 0);
                    Intent intent = new Intent(ACTION_DATE_RANGE);
                    intent.putExtra("start", this.start);
                    intent.putExtra("end", this.end);
                    LocalBroadcastManager.getInstance(appCompatActivity).sendBroadcast(intent);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(start);
                    int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                    int month1 = calendar.get(Calendar.MONTH);
                    int year1 = calendar.get(Calendar.YEAR);
                    Date d1 = calendar.getTime();
                    calendar.setTimeInMillis(end);
                    int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                    int month2 = calendar.get(Calendar.MONTH);
                    int year2 = calendar.get(Calendar.YEAR);
                    Date d2 = calendar.getTime();
                    String f = "";
                    if (month2 == month1 && year1 == year2) {
                        f = String.format("%s - %s %s", day1, day2, Util.dateToString(d1, Constants.DATE_FORMAT_5));
                    } else {
                        f = String.format("%s %s - %s %s", day1, Util.dateToString(d1, Constants.DATE_FORMAT_5),
                                day2, Util.dateToString(d2, Constants.DATE_FORMAT_5));
                    }
                    dateRange.setText(f);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
