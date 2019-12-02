package moneyassistant.expert.ui.transaction.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.repository.TransactionRepository;
import moneyassistant.expert.util.SharedDataHandler;

import static moneyassistant.expert.util.DateTimeUtil.DATE_FORMAT_DAY_MONTH_YEAR;
import static moneyassistant.expert.util.DateTimeUtil.getCurrentDate;
import static moneyassistant.expert.util.DateTimeUtil.getFirstDayOf;
import static moneyassistant.expert.util.DateTimeUtil.getLastDayOf;
import static moneyassistant.expert.util.SharedDataHandler.DISPLAY_FIELD_TYPE;
import static moneyassistant.expert.util.SharedDataHandler.DISPLAY_PATTERN_TYPE;

/**
 * MoneyAssistant
 * Created by catalin on 16.11.2019
 */
public class TransactionViewModel extends ViewModel {

    private final TransactionRepository repository;
    private final SharedDataHandler sharedDataHandler;
    private MutableLiveData<Calendar> calendar = new MutableLiveData<>();
    private LiveData<List<TransactionWithCA>> source;
    private MediatorLiveData<List<TransactionWithCA>> transactions = new MediatorLiveData<>();
    private String pattern;
    private int field;
    private Calendar c = Calendar.getInstance();

    @Inject
    TransactionViewModel(TransactionRepository repository, SharedDataHandler sharedDataHandler) {
        this.repository = repository;
        this.sharedDataHandler = sharedDataHandler;
        this.calendar.setValue(c);
        this.field = sharedDataHandler.getInteger(DISPLAY_FIELD_TYPE, Calendar.DAY_OF_YEAR);
        this.pattern = sharedDataHandler.getString(DISPLAY_PATTERN_TYPE, DATE_FORMAT_DAY_MONTH_YEAR);
        setupTransactionSource();
    }

    public MutableLiveData<Calendar> getCalendar() {
        return calendar;
    }

    public String getPattern() {
        return pattern;
    }

    public MediatorLiveData<List<TransactionWithCA>> getTransactions() {
        return transactions;
    }

    public void setupDate(String pattern, int field) {
        c = Calendar.getInstance();
        this.pattern = pattern;
        this.field = field;
        this.calendar.setValue(c);
        setupTransactionSource();
        sharedDataHandler.storeInteger(DISPLAY_FIELD_TYPE, field);
        sharedDataHandler.storeString(DISPLAY_PATTERN_TYPE, pattern);
    }

    public void next() {
        c.add(field, 1);
        this.calendar.setValue(c);
        setupTransactionSource();
    }

    public void prev() {
        c.add(field, -1);
        this.calendar.setValue(c);
        setupTransactionSource();
    }

    private void setupTransactionSource() {
        LiveData<List<TransactionWithCA>> source;
        if (field == Calendar.DAY_OF_YEAR) {
            source = repository.findAllByDate(getCurrentDate(c));
        } else {
            source = repository.findAllByInterval(getFirstDayOf(c, field), getLastDayOf(c, field));
        }

        if (this.source != null) {
            this.transactions.removeSource(this.source);
        }
        this.source = source;
        this.transactions.addSource(this.source, transactionList ->
                this.transactions.setValue(transactionList));
    }

}
