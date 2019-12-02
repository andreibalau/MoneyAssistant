package moneyassistant.expert.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import moneyassistant.expert.model.entity.Account;

@Dao
public interface AccountDao {

    @Insert
    void insert(Account account);

    @Delete
    void delete(Account account);

    @Query(value = "delete from accounts where account_id = :accountId")
    void deleteById(Long accountId);

    @Update
    void update(Account account);

    @Query(value = "select * from accounts where account_id = :id")
    LiveData<Account> findById(Long id);

    @Query(value = "select * from accounts")
    LiveData<List<Account>> findAll();

    @Query(value = "select count(*) from transactions where account_id_f = :accountId")
    Integer countTransactionsByAccountId(Long accountId);

}
