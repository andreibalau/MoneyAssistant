package moneyassistant.expert.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.viewmodel.TransactionViewModel;

import static moneyassistant.expert.view.fragment.Reports.ACTION_DATE_RANGE;

public class IncomeReports extends Fragment implements OnChartValueSelectedListener {

    private static final String TAG = "IncomeReports";
    private PieChart pieChart;
    private TransactionViewModel transactionViewModel;
    private AppCompatActivity appCompatActivity;
    private CoordinatorLayout coordinatorLayout;
    private String currency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_report, container, false);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        pieChart = view.findViewById(R.id.chart);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.getLegend().setEnabled(false);
        Calendar calendar = Calendar.getInstance();
        int minmDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int maxmDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, minmDay);
        Date start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, maxmDay);
        Date end = calendar.getTime();
        transactionViewModel.getTransactions(start, end, Transaction.TransactionTypes.Income)
                .observe(getViewLifecycleOwner(), transactionWithCAS -> {
            Map<String, Double> map = groupList(transactionWithCAS);
            setData(map);
        });
        return view;
    }

    private Map<String, Double> groupList(List<TransactionWithCA> transactionWithCAS) {
        Map<String, Double> map = new HashMap<>();
        for (TransactionWithCA transactionWithCA : transactionWithCAS) {
            Transaction transaction = transactionWithCA.getTransaction();
            Category category = transactionWithCA.getCategory();
            if (map.containsKey(category.getName())) {
                double value = map.get(category.getName());
                map.put(category.getName(), value + transaction.getAmount());
            } else {
                map.put(category.getName(), transaction.getAmount());
            }
        }
        return map;
    }

    private void setData(Map<String, Double> map) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Transactions");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
        IntentFilter f = new IntentFilter(ACTION_DATE_RANGE);
        LocalBroadcastManager.getInstance(appCompatActivity).registerReceiver(event, f);
        currency = Util.getFromSharedPreferences(context, Constants.PREFERED_CURRENCY);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        LocalBroadcastManager.getInstance(appCompatActivity).unregisterReceiver(event);
        super.onDetach();
    }

    private BroadcastReceiver event = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long start = intent.getLongExtra("start", 0);
            long end = intent.getLongExtra("end", 0);
            Date d1 = new Date(start);
            Date d2 = new Date(end);
            transactionViewModel.getTransactions(d1, d2, Transaction.TransactionTypes.Income)
                    .removeObservers(appCompatActivity);
            transactionViewModel.getTransactions(d1, d2, Transaction.TransactionTypes.Income)
                    .observe(appCompatActivity, transactionWithCAS -> {
                        Map<String, Double> map = groupList(transactionWithCAS);
                        setData(map);
                    });
        }
    };

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout,
                String.format("Value: %s %s", e.getY(), currency),
                Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", (v) -> {
            snackbar.dismiss();
        });
        snackbar.setActionTextColor(appCompatActivity.getColor(R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void onNothingSelected() {

    }
}
