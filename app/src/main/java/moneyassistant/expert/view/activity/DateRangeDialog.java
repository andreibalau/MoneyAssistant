package moneyassistant.expert.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import moneyassistant.expert.R;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.Util;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DateRangeDialog extends AppCompatActivity {

    private TextView date1;
    private TextView date2;

    private long start;
    private long end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range_dialog);
        getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
        setTitle("Select interval");
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        Intent intent = getIntent();
        start = intent.getLongExtra("start", 0);
        end = intent.getLongExtra("end", 0);
        Date d1 = new Date(start);
        Date d2 = new Date(end);
        date1.setText(Util.dateToString(d1, Constants.DATE_FORMAT_4));
        date2.setText(Util.dateToString(d2, Constants.DATE_FORMAT_4));
        date1.setOnClickListener((v) -> {
            Calendar mcurrentTime = Calendar.getInstance();
            mcurrentTime.setTimeInMillis(start);
            int year = mcurrentTime.get(Calendar.YEAR);
            int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
            int month = mcurrentTime.get(Calendar.MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(DateRangeDialog.this,
                    (view1, year1, month1, dayOfMonth) -> {
                        mcurrentTime.set(year1, month1, dayOfMonth);
                        start = mcurrentTime.getTime().getTime();
                        Date d = new Date(start);
                        date1.setText(Util.dateToString(d, Constants.DATE_FORMAT_4));
                    }, year, month, day);
            datePickerDialog.setTitle("Select date");
            datePickerDialog.show();
        });
        date2.setOnClickListener((v) -> {
            Calendar mcurrentTime = Calendar.getInstance();
            mcurrentTime.setTimeInMillis(end);
            int year = mcurrentTime.get(Calendar.YEAR);
            int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
            int month = mcurrentTime.get(Calendar.MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(DateRangeDialog.this,
                    (view1, year1, month1, dayOfMonth) -> {
                        mcurrentTime.set(year1, month1, dayOfMonth);
                        end = mcurrentTime.getTime().getTime();
                        Date d = new Date(end);
                        date2.setText(Util.dateToString(d, Constants.DATE_FORMAT_4));
                    }, year, month, day);
            datePickerDialog.setTitle("Select date");
            datePickerDialog.show();
        });
    }

    public void save(View v) {
        Intent intent = new Intent();
        intent.putExtra("start", this.start);
        intent.putExtra("end", this.end);
        setResult(Constants.DATE_RANGE_RESULT_CODE, intent);
        finish();
    }
}
