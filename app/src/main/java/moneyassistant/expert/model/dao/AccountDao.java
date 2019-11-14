package moneyassistant.expert.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import moneyassistant.expert.model.entity.Account;

@Dao
public interface AccountDao {

    @Insert
    void insert(Account account);

    @Delete
    void delete(Account account);

    @Query(value = "delete from account where account_id = :accountId")
    void delete(long accountId);

    @Update
    void update(Account account);

    @Query(value = "select * from account where account_id = :id")
    LiveData<Account> getAccount(long id);

    @Query(value = "select * from account")
    LiveData<List<Account>> getAccounts();

    @Query(value = "select count(*) from money_transaction where account_id_f = :accountId")
    int checkTransactions(long accountId);
}
