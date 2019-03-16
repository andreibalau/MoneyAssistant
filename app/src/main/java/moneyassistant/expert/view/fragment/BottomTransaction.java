package moneyassistant.expert.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.FragmentEvent;
import moneyassistant.expert.util.Util;
import moneyassistant.expert.viewmodel.AccountViewModel;
import moneyassistant.expert.viewmodel.TransactionViewModel;
import moneyassistant.expert.viewmodel.adapter.AccountSpinnerAdapter;

public class BottomTransaction extends BottomSheetDialogFragment {

    private FragmentEvent fragmentEvent;
    private Context context;
    private TransactionViewModel transactionViewModel;
    private AccountViewModel accountViewModel;

    private AccountSpinnerAdapter accountSpinnerAdapter;

    private EditText details;
    private TextView date;
    private Spinner account;
    private EditText amount;

    private String time;
    private TransactionWithCA transaction;

    public void setTransaction(TransactionWithCA transaction) {
        this.transaction = transaction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme_BottomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_transaction, container, false);
        details = v.findViewById(R.id.details);
        ImageView icon = v.findViewById(R.id.icon);
        date = v.findViewById(R.id.date);
        account = v.findViewById(R.id.account);
        amount = v.findViewById(R.id.amount);
        accountSpinnerAdapter = new AccountSpinnerAdapter(context, new ArrayList<>());
        account.setAdapter(accountSpinnerAdapter);
        Button button = v.findViewById(R.id.save);
        button.setOnClickListener(view -> save());
        date.setOnClickListener(view -> changeDate());
        Transaction t = transaction.getTransaction();
        if (t.getDetails() != null) {
            details.setText(t.getDetails());
        }
        if (t.getDate() != null) {
            time = t.getDate();
            date.setText(time);
        }
        if (transaction.getCategory() != null) {
            int id = context.getResources()
                    .getIdentifier(transaction.getCategory().getIcon(),
                            "drawable", context.getPackageName());
            Drawable drawable = context.getDrawable(id);
            icon.setImageDrawable(drawable);
        }
        if (t.getAmount() > 0) {
            amount.setText(String.valueOf(t.getAmount()));
        }
        accountViewModel.getAccounts().observe(this, accounts -> {
            accountSpinnerAdapter.submitList(accounts);
            for (Account a : accounts) {
                if (a.getId() == t.getAccountId()) {
                    account.setSelection(accounts.indexOf(a));
                    break;
                }
            }
        });
        return v;
    }

    private void save() {
        if (amount.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.no_amount), Toast.LENGTH_SHORT).show();
            return;
        }
        double amt = Double.parseDouble(amount.getText().toString());
        if (amt == 0) {
            Toast.makeText(context, getString(R.string.no_amount), Toast.LENGTH_SHORT).show();
            return;
        }
        Account a = accountSpinnerAdapter.getAccountAt(account.getSelectedItemPosition());
        Transaction t = transaction.getTransaction();
        String d = details.getText().toString();
        if (d.equals("")) {
            t.setDetails(transaction.getCategory().getName());
        } else {
            t.setDetails(d);
        }
        t.setAmount(amt);
        t.setDate(time);
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = Util.stringToDate(time, Constants.DATE_FORMAT_1);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        t.setAccountId(a.getId());
        a.setCurrentAmount(t.getType().equals(Transaction.TransactionTypes.Expense)
                ? a.getCurrentAmount() - amt : a.getCurrentAmount() + amt);
        t.setDay(day);
        t.setMonth(month);
        t.setYear(year);
        if (t.getId() == 0) {
            transactionViewModel.insert(t);
        } else {
            transactionViewModel.update(t);
        }
        accountViewModel.update(a);
        fragmentEvent.onSave();
        dismiss();
    }

    private void changeDate() {
        Calendar mcurrentTime = Calendar.getInstance();
        try {
            mcurrentTime.setTime(Util.stringToDate(date.getText().toString(), Constants.DATE_FORMAT_1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = mcurrentTime.get(Calendar.YEAR);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(context,
                (view1, year1, month1, dayOfMonth) -> {
                    Calendar c = Calendar.getInstance();
                    c.set(year1, month1, dayOfMonth);
                    time = Util.dateToString(c.getTime(), Constants.DATE_FORMAT_1);
                    date.setText(time);
                }, year, month, day);
        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        fragmentEvent = (FragmentEvent) context;
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }
}
