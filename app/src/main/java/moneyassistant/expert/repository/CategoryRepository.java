package moneyassistant.expert.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.database.CategoryDao;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.Type;

public class CategoryRepository {

    private final CategoryDao categoryDao;

    @Inject
    public CategoryRepository(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void update(final Category category) {
        new Thread(() -> categoryDao.update(category)).start();
    }

    public void insert(final Category category) {
        new Thread(() -> categoryDao.insert(category)).start();
    }

    public void delete(final Category category) {
        new Thread(() -> categoryDao.delete(category)).start();
    }

    public void delete(long categoryId) {
        new Thread(() -> categoryDao.delete(categoryId)).start();
    }

    public LiveData<Category> findById(long id) {
        return categoryDao.findById(id);
    }

    public LiveData<List<Category>> findAllByType(Type type) {
        return categoryDao.findAllByType(type.name());
    }

}
