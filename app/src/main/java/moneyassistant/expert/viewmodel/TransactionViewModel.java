package moneyassistant.expert.viewmodel;

import android.app.Application;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.entity.TransactionWithCA;
import moneyassistant.expert.model.repository.TransactionRepository;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
    }

    public void insert(Transaction transaction) {
        transactionRepository.insert(transaction);
    }

    public void update(Transaction transaction) {
        transactionRepository.update(transaction);
    }

    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public LiveData<TransactionWithCA> getTransactionById(long id) {
        return transactionRepository.getTransactionById(id);
    }

    public LiveData<List<TransactionWithCA>> getTransactions(Date s, Date e) {
        return transactionRepository.getTransactions(s, e);
    }
}
