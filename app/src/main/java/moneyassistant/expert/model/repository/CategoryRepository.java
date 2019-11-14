package moneyassistant.expert.model.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

import androidx.lifecycle.LiveData;
import moneyassistant.expert.model.AppDatabase;
import moneyassistant.expert.model.dao.CategoryDao;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.util.OnCheckModelCount;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private OnCheckModelCount onCheckModelCount;

    public CategoryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        categoryDao = appDatabase.getCategoryDao();
    }

    public void setOnCheckModelCount(OnCheckModelCount onCheckModelCount) {
        this.onCheckModelCount = onCheckModelCount;
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

    public void checkTransactions(long categoryId) {
        new Thread(() -> {
            int count = categoryDao.checkTransactions(categoryId);
            new Handler(Looper.getMainLooper()).post(() -> onCheckModelCount.onCheck(count));
        }).start();
    }

    public LiveData<Category> getCategoryById(long id) {
        return categoryDao.getCategory(id);
    }

    public LiveData<List<Category>> getCategories(String categoryType) {
        return categoryDao.getCategories(categoryType);
    }
}
