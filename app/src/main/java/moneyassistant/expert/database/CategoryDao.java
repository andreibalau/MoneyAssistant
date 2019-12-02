package moneyassistant.expert.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import moneyassistant.expert.model.entity.Category;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query(value = "delete from categories where category_id = :categoryId")
    void delete(Long categoryId);

    @Query(value = "select * from categories where category_id = :id")
    LiveData<Category> findById(Long id);

    @Query(value = "select * from categories where category_type = :type")
    LiveData<List<Category>> findAllByType(String type);

    @Query(value = "select count(*) from transactions where category_id_f = :categoryId")
    Integer countTransactionsByCategoryId(Long categoryId);

}
