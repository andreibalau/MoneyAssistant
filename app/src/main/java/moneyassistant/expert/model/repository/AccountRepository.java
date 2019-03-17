package moneyassistant.expert.model.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

import androidx.lifecycle.LiveData;
import moneyassistant.expert.model.AppDatabase;
import moneyassistant.expert.model.dao.AccountDao;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.util.OnCheckModelCount;

public class AccountRepository {

    private AccountDao accountDao;
    private OnCheckModelCount onCheckModelCount;

    public AccountRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        accountDao = appDatabase.getAccountDao();
    }

    public void setOnCheckModelCount(OnCheckModelCount onCheckModelCount) {
        this.onCheckModelCount = onCheckModelCount;
    }

    public void insert(final Account account) {
        new Thread(() -> accountDao.insert(account)).start();
    }

    public void update(final Account account) {
        new Thread(() -> accountDao.update(account)).start();
    }

    public void delete(final Account account) {
        new Thread(() -> accountDao.delete(account)).start();
    }

    public void delete(long accountId) {
        new Thread(() -> accountDao.delete(accountId)).start();
    }

    public void checkTransactions(long accountId) {
        new Thread(() -> {
            int count = accountDao.checkTransactions(accountId);
            new Handler(Looper.getMainLooper()).post(() -> onCheckModelCount.onCheck(count));
        }).start();
    }

    public void computeAccountValue(Account account) {
        new Thread(() -> {
            List<Transaction> transactions = accountDao.getTransactions(account.getId());
            double starting = account.getStartingAmount();
            double income = 0, expense = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getType().equals(Transaction.TransactionTypes.Income)) {
                    income += transaction.getAmount();
                } else if (transaction.getType().equals(Transaction.TransactionTypes.Expense)) {
                    expense += transaction.getAmount();
                }
            }
            double result = starting + income - expense;
            account.setCurrentAmount(result);
            accountDao.update(account);
        }).start();
    }

    public LiveData<Account> getAccountById(long id) {
        return accountDao.getAccount(id);
    }

    public LiveData<List<Account>> getAccounts() {
        return accountDao.getAccounts();
    }
}
