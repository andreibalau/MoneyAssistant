package moneyassistant.expert.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.database.AccountDao;
import moneyassistant.expert.model.entity.Account;

public class AccountRepository {

    private final AccountDao accountDao;

    @Inject
    public AccountRepository(AccountDao accountDao) {
        this.accountDao = accountDao;
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

    public void deleteById(long accountId) {
        new Thread(() -> accountDao.deleteById(accountId)).start();
    }

    public LiveData<Account> findById(long id) {
        return accountDao.findById(id);
    }

    public LiveData<List<Account>> findAll() {
        return accountDao.findAll();
    }

}
