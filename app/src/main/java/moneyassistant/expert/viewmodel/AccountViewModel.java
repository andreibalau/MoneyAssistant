package moneyassistant.expert.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.repository.AccountRepository;
import moneyassistant.expert.util.OnCheckModelCount;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    public void setOnCheckModelCount(OnCheckModelCount onCheckModelCount) {
        accountRepository.setOnCheckModelCount(onCheckModelCount);
    }

    public void checkTransactions(long accountId) {
        accountRepository.checkTransactions(accountId);
    }

    public void insert(Account account) {
        accountRepository.insert(account);
    }

    public void update(Account account) {
        accountRepository.update(account);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }

    public void delete(long accountId) {
        accountRepository.delete(accountId);
    }

    public LiveData<Account> getAccountById(long id) {
        return accountRepository.getAccountById(id);
    }

    public LiveData<List<Account>> getAccounts() {
        return accountRepository.getAccounts();
    }
}
