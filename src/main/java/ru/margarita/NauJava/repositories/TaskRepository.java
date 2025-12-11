package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.Task;
import java.util.List;

/**
 * Класс взаимодействия с таблицей задач
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@RepositoryRestResource(path = "tasks")
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name LIKE %:name%")
    List<Task> findTasksByUserName(String name);

    void deleteByUserId(Long id);
}