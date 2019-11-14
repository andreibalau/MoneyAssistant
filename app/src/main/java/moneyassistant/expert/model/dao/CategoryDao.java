package moneyassistant.expert.model.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import moneyassistant.expert.model.entity.Category;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query(value = "delete from category where category_id = :categoryId")
    void delete(long categoryId);

    @Query(value = "select * from category where category_id = :id")
    LiveData<Category> getCategory(long id);

    @Query(value = "select * from category where category_type = :categoryType")
    LiveData<List<Category>> getCategories(String categoryType);

    @Query(value = "select count(*) from money_transaction where category_id_f = :categoryId")
    int checkTransactions(long categoryId);
}
