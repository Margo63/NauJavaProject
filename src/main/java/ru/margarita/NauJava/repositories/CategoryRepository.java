package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.entities.CategoryCode;

/**
 * Класс взаимодействия с таблицей категорий
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByCode(CategoryCode code);
}
