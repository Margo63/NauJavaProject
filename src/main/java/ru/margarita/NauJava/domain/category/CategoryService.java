package ru.margarita.NauJava.domain.category;

import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.entities.Status;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
}
