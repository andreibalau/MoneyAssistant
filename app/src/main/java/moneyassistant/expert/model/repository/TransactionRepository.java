package moneyassistant.expert.model.repository;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import moneyassistant.expert.model.AppDatabase;
import moneyassistant.expert.model.dao.TransactionDao;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;

public class TransactionRepository {

    private TransactionDao transactionDao;

    public TransactionRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        transactionDao = appDatabase.getTransactionDao();
    }

    public void insert(final Transaction transaction) {
        new Thread(() -> {
            transactionDao.insert(transaction);
            computeAccountsValue();
        }).start();
    }

    public void update(final Transaction transaction) {
        new Thread(() -> {
            transactionDao.update(transaction);
            computeAccountsValue();
        }).start();
    }

    public void delete(final Transaction transaction) {
        new Thread(() -> {
            transactionDao.delete(transaction);
            computeAccountsValue();
        }).start();
    }

    private void computeAccountsValue() {
        List<Long> accountIds = transactionDao.getAccountIds();
        for (Long accountId : accountIds) {
            double income = transactionDao.getTransactionSum(accountId, Transaction.TransactionTypes.Income);
            double expense = transactionDao.getTransactionSum(accountId, Transaction.TransactionTypes.Expense);
            double result = income - expense;
            transactionDao.computeAccountValue(result, accountId);
        }
    }

    public LiveData<TransactionWithCA> getTransactionById(long id) {
        return transactionDao.getTransaction(id);
    }

    public LiveData<List<TransactionWithCA>> getTransactions(Date s, Date e) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(s);
        int day1, month1, year1, day2, month2, year2;
        day1 = calendar.get(Calendar.DAY_OF_MONTH);
        month1 = calendar.get(Calendar.MONTH);
        year1 = calendar.get(Calendar.YEAR);
        calendar.setTime(e);
        day2 = calendar.get(Calendar.DAY_OF_MONTH);
        month2 = calendar.get(Calendar.MONTH);
        year2 = calendar.get(Calendar.YEAR);
        return transactionDao.getTransactions(day1, month1, year1, day2, month2, year2);
    }

    public LiveData<List<TransactionWithCA>> getTransactions(Date s, Date e, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(s);
        int day1, month1, year1, day2, month2, year2;
        day1 = calendar.get(Calendar.DAY_OF_MONTH);
        month1 = calendar.get(Calendar.MONTH);
        year1 = calendar.get(Calendar.YEAR);
        calendar.setTime(e);
        day2 = calendar.get(Calendar.DAY_OF_MONTH);
        month2 = calendar.get(Calendar.MONTH);
        year2 = calendar.get(Calendar.YEAR);
        return transactionDao.getTransactions(day1, month1, year1, day2, month2, year2, type);
    }
}
