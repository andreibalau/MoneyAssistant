package moneyassistant.expert.ui.account.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.repository.AccountRepository;

public class AccountViewModel extends ViewModel {

    private final AccountRepository repository;

    @Inject
    public AccountViewModel(AccountRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Account>> findAllAccounts() {
        return repository.findAll();
    }

    public LiveData<Account> findAccountById(Long id) {
        return repository.findById(id);
    }

    public void save(Account account) {
        if (account.getId() == null) {
            repository.insert(account);
        } else {
            repository.update(account);
        }
    }

    public void delete(Account account) {
        repository.delete(account);
    }

}
