package moneyassistant.expert.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import moneyassistant.expert.model.entity.Transaction;
import moneyassistant.expert.model.TransactionWithCA;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("select * from transactions t " +
            "inner join categories c on t.category_id_f == c.category_id " +
            "inner join accounts a on t.account_id_f == a.account_id " +
            "where t.transaction_id = :id")
    LiveData<TransactionWithCA> findById(Long id);

    @Query("select * from transactions t " +
            "inner join categories c on t.category_id_f == c.category_id " +
            "inner join accounts a on t.account_id_f == a.account_id " +
            "where t.transaction_date = :date " +
            "order by t.transaction_date asc")
    LiveData<List<TransactionWithCA>> findAllByDate(Long date);

    @Query("select * from transactions t " +
            "inner join categories c on t.category_id_f == c.category_id " +
            "inner join accounts a on t.account_id_f == a.account_id " +
            "where t.transaction_date >= :date1 and t.transaction_date <= :date2 " +
            "order by t.transaction_date asc")
    LiveData<List<TransactionWithCA>> findAllByInterval(Long date1, Long date2);

}
