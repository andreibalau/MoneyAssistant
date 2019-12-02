package moneyassistant.expert.ui.transaction.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.model.Type;
import moneyassistant.expert.repository.AccountRepository;
import moneyassistant.expert.repository.TransactionRepository;

/**
 * MoneyAssistant
 * Created by catalin on 30.11.2019
 */
public class ViewTransactionViewModel extends ViewModel {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;
    private TransactionWithCA transaction;

    @Inject
    ViewTransactionViewModel(TransactionRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public void setTransaction(TransactionWithCA transaction) {
        this.transaction = transaction;
    }

    public TransactionWithCA getTransaction() {
        return transaction;
    }

    public LiveData<TransactionWithCA> fetchTransaction(Long id) {
        return repository.findById(id);
    }

    public void delete() {
        if (transaction != null) {
            Transaction t = transaction.getTransaction();
            repository.delete(t);
            Account account = transaction.getAccount();
            if (t.getType().equals(Type.EXPENSE.name())) {
                account.setCurrentAmount(account.getCurrentAmount() + t.getAmount());
            } else {
                account.setCurrentAmount(account.getCurrentAmount() - t.getAmount());
            }
            accountRepository.update(account);
        }
    }

}
