package ru.margarita.NauJava.domain.category;

import ru.margarita.NauJava.entities.Category;

import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
public interface CategoryService {

    /**
    * метод для получения всех категорий
    * */
    List<Category> getAllCategories();
}
