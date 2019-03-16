package moneyassistant.expert.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.repository.CategoryRepository;
import moneyassistant.expert.util.OnCheckModelCount;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public void setOnCheckModelCount(OnCheckModelCount onCheckModelCount) {
        categoryRepository.setOnCheckModelCount(onCheckModelCount);
    }

    public void insert(Category category) {
        categoryRepository.insert(category);
    }

    public void update(Category category) {
        categoryRepository.update(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public void delete(long categoryId) {
        categoryRepository.delete(categoryId);
    }

    public LiveData<Category> getCategoryById(long id) {
        return categoryRepository.getCategoryById(id);
    }

    public void checkTransactions(long categoryId) {
        categoryRepository.checkTransactions(categoryId);
    }

    public LiveData<List<Category>> getCategories(String categoryType) {
        return categoryRepository.getCategories(categoryType);
    }
}
