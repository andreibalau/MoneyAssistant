package moneyassistant.expert.ui.transaction.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import moneyassistant.expert.MoneyAssistant;
import moneyassistant.expert.R;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.ui.transaction.adapter.AccountSpinnerAdapter;
import moneyassistant.expert.ui.transaction.listener.OnTransactionSaveListener;
import moneyassistant.expert.ui.transaction.viewmodel.SaveTransactionViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_DAY_MONTH_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.dateToString;

public class BottomTransaction extends BottomSheetDialogFragment {

    @BindView(R.id.details)
    EditText details;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.account)
    Spinner account;
    @BindView(R.id.amount)
    EditText amount;
    @BindView(R.id.icon)
    ImageView icon;
    @Inject
    IntelViewModelFactory factory;

    private Context context;
    private TransactionWithCA transactionWithCA;
    private OnTransactionSaveListener listener;
    private Unbinder unbinder;
    private AccountSpinnerAdapter accountSpinnerAdapter;
    private SaveTransactionViewModel viewModel;

    public static BottomTransaction getInstance(TransactionWithCA transactionWithCA,
                                                OnTransactionSaveListener listener) {
        BottomTransaction bottomTransaction = new BottomTransaction();
        bottomTransaction.transactionWithCA = transactionWithCA;
        bottomTransaction.listener = listener;
        return bottomTransaction;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        unbinder = ButterKnife.bind(this, v);
        MoneyAssistant.getAppComponent().inject(this);
        accountSpinnerAdapter = new AccountSpinnerAdapter(context, new ArrayList<>());
        account.setAdapter(accountSpinnerAdapter);
        viewModel = new ViewModelProvider(this, factory).get(SaveTransactionViewModel.class);
        viewModel.setTransaction(transactionWithCA);
        viewModel
                .findAccounts()
                .observe(getViewLifecycleOwner(), this::consumeAccounts);
        if (transactionWithCA.getCategory() != null) {
            int id = context.getResources()
                    .getIdentifier(transactionWithCA.getCategory().getIcon(),
                            "drawable", context.getPackageName());
            Drawable drawable = context.getDrawable(id);
            icon.setImageDrawable(drawable);
        }
        Transaction transaction = transactionWithCA.getTransaction();
        String details = transaction.getDetails();
        Double amount = transaction.getAmount();
        Long date = transaction.getDate();
        if (details != null) {
            this.details.setText(details);
        }
        if (date != null) {
            this.date.setText(dateToString(date, DATE_FORMAT_DAY_MONTH_YEAR));
        }
        if (amount != null && amount > 0) {
            this.amount.setText(String.valueOf(amount));
        }
        return v;
    }

    @OnTextChanged(value = R.id.details, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onDetailsChanged(CharSequence details) {
        viewModel.setDetails(details.toString());
    }

    @OnTextChanged(value = R.id.amount, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onAmountChanged(CharSequence amount) {
        if (amount.equals("")) {
            return;
        }
        viewModel.setAmount(Double.valueOf(amount.toString()));
    }

    @OnItemSelected(R.id.account)
    void onAccountSelected(int position) {
        viewModel.setAccount(accountSpinnerAdapter.getAccountAt(position));
    }

    @OnClick(R.id.save)
    void save() {
        Transaction transaction = viewModel.getTransaction();
        if (transaction.getAmount() == null || transaction.getAmount() == 0) {
            Toast.makeText(context, getString(R.string.no_amount), Toast.LENGTH_SHORT).show();
            return;
        }
        if (transaction.getDetails() == null || transaction.getDetails().equals("")) {
            viewModel.setDetails(transactionWithCA.getCategory().getName());
        }
        viewModel.save();
        dismiss();
        if (listener != null) {
            listener.onSave();
        }
    }

    @OnClick(R.id.date)
    void changeDate() {
        Calendar mcurrentTime = Calendar.getInstance();
        mcurrentTime.setTimeInMillis(viewModel.getTransaction().getDate());
        int year = mcurrentTime.get(Calendar.YEAR);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentTime.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view1, year1, month1, dayOfMonth) -> {
                    Calendar c = Calendar.getInstance();
                    c.set(year1, month1, dayOfMonth);
                    viewModel.setDate(c);
                    date.setText(dateToString(c.getTimeInMillis(), DATE_FORMAT_DAY_MONTH_YEAR));
                }, year, month, day);
        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void consumeAccounts(List<Account> accounts) {
        accountSpinnerAdapter.submitList(accounts);
        if (accounts.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.no_accounts);
            builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
            builder.create().show();
            dismiss();
            return;
        }
        for (Account a : accounts) {
            if (a.getId().equals(transactionWithCA.getTransaction().getAccountId())) {
                account.setSelection(accounts.indexOf(a));
                break;
            }
        }
    }

}
