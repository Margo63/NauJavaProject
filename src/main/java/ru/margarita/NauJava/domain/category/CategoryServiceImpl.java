package ru.margarita.NauJava.domain.category;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.domain.task.TaskService;
import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.repositories.CategoryRepository;

import java.util.List;

/**
 * Реализация {@link CategoryService}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }
}
