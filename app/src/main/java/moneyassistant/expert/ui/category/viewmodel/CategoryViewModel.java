package moneyassistant.expert.ui.category.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import moneyassistant.expert.model.Type;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.repository.CategoryRepository;

public class CategoryViewModel extends ViewModel {

    private final CategoryRepository repository;

    @Inject
    CategoryViewModel(CategoryRepository repository) {
        this.repository = repository;
    }

    public LiveData<Category> findCategoryById(Long id) {
        return repository.findById(id);
    }

    public LiveData<List<Category>> findAllCategoriesByType(Type type) {
        return repository.findAllByType(type);
    }

    public void save(Category category) {
        if (category.getId() == null) {
            repository.insert(category);
        } else {
            repository.update(category);
        }
    }

    public void delete(Category category) {
        repository.delete(category);
    }

}
