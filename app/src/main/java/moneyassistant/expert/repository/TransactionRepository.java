package moneyassistant.expert.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.database.TransactionDao;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.TransactionWithCA;

public class TransactionRepository {

    private final TransactionDao transactionDao;

    @Inject
    public TransactionRepository(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void insert(final Transaction transaction) {
        new Thread(() -> {
            transactionDao.insert(transaction);
        }).start();
    }

    public void update(final Transaction transaction) {
        new Thread(() -> {
            transactionDao.update(transaction);
        }).start();
    }

    public void delete(final Transaction transaction) {
        new Thread(() -> {
            transactionDao.delete(transaction);
        }).start();
    }

    public LiveData<List<TransactionWithCA>> findAllByDate(Long date) {
        return transactionDao.findAllByDate(date);
    }

    public LiveData<List<TransactionWithCA>> findAllByInterval(Long date1, Long date2) {
        return transactionDao.findAllByInterval(date1, date2);
    }

    public LiveData<TransactionWithCA> findById(Long id) {
        return transactionDao.findById(id);
    }

}
