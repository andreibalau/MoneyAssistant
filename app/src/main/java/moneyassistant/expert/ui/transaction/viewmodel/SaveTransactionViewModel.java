package moneyassistant.expert.ui.transaction.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.TransactionWithCA;
import moneyassistant.expert.model.Type;
import moneyassistant.expert.repository.AccountRepository;
import moneyassistant.expert.repository.TransactionRepository;

import static moneyassistant.expert.util.DateTimeUtil.getCurrentDate;

/**
 * MoneyAssistant
 * Created by catalin on 27.11.2019
 */
public class SaveTransactionViewModel extends ViewModel {

    private TransactionWithCA transactionWithCA = new TransactionWithCA();
    private Transaction transaction = new Transaction();

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Inject
    SaveTransactionViewModel(TransactionRepository transactionRepository,
                             AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public LiveData<List<Account>> findAccounts() {
        return accountRepository.findAll();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionWithCA transactionWithCA) {
        this.transactionWithCA = transactionWithCA;
        this.transaction = transactionWithCA.getTransaction();
    }

    public void setDetails(String details) {
        this.transaction.setDetails(details);
    }

    public void setDate(Calendar calendar) {
        this.transaction.setDate(getCurrentDate(calendar));
    }

    public void setAmount(Double amount) {
        this.transaction.setAmount(amount);
    }

    public void setAccount(Account account) {
        this.transaction.setAccountId(account.getId());
        this.transactionWithCA.setAccount(account);
    }

    public void save() {
        transaction.setCategoryId(transactionWithCA.getCategory().getId());
        if (transaction.getId() == null || transaction.getId() == 0) {
            transactionRepository.insert(transaction);
        } else {
            transactionRepository.update(transaction);
        }
        Account account = transactionWithCA.getAccount();
        if (transaction.getType().equals(Type.EXPENSE.name())) {
            account.setCurrentAmount(account.getCurrentAmount() - transaction.getAmount());
        } else {
            account.setCurrentAmount(account.getCurrentAmount() + transaction.getAmount());
        }
        accountRepository.update(account);
    }

}
