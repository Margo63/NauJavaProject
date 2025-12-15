package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.entities.Task;

import java.util.Date;
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

    /**
     * поиск задач по имени пользователя
     * */
    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name = %:name%")
    List<Task> findTasksByUserName(String name);


    /**
     * поиск задач по имени пользователя и категории
     * */
    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name = :name AND u.category.id = :categoryId")
    List<Task> findTasksByUserNameAndCategoryId(@Param("name") String name, @Param("categoryId") Long categoryId);

    /**
     * поиск задач по имени пользователя, статусу и категории
     * */
    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name = :name AND u.category.id = :categoryId AND u.status.id = :statusId")
    List<Task> findTasksByUserNameAndCategoryIdAndStatusId(@Param("name") String name, @Param("categoryId") Long categoryId,
                                                           @Param("statusId") Long statusId);

    /**
     * поиск задач по имени пользователя и статусу
     * */
    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name = :name AND u.status.id = :statusId")
    List<Task> findTasksByUserNameAndStatusId(@Param("name") String name, @Param("statusId") Long statusId);

    /**
     * обновление статуса задачи
     * */
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status  WHERE t.id = :id")
    void updateTaskStatus(@Param("id") Long id, @Param("status") Status status);

    /**
     * обновление категории задачи
     * */
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.category = :category  WHERE t.id = :id")
    void updateTaskCategory(@Param("id") Long id, @Param("category") Category category);


    /**
     * обновление задачи
     * */
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET  t.title = :title, t.description = :description, t.category = :category, t.status = :status, t.dueDate = :date WHERE t.id = :id")
    void updateTask(@Param("id") Long id, @Param("title") String title, @Param("description") String description,
                    @Param("category") Category category, @Param("status") Status status, @Param("date") Date date);

    /**
     * обновление таймера задачи
     * */
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET  t.timerValue = :timerValue WHERE t.id = :id")

    void updateTimerValue(@Param("id")Long id,@Param("timerValue") int timerValue);
}