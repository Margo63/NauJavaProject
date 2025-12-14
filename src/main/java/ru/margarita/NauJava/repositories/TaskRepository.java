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

    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name LIKE :name AND u.category.id = :categoryId")
    List<Task> findTasksByUserNameAndCategoryId( @Param("name")String name,@Param("categoryId") Long categoryId);

    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name LIKE :name AND u.category.id = :categoryId AND u.status.id = :statusId")
    List<Task> findTasksByUserNameAndCategoryIdAndStatusId(@Param("name")String name,@Param("categoryId") Long categoryId,
                                                           @Param("statusId") Long statusId);
    @Query("SELECT DISTINCT u FROM Task u JOIN u.user t WHERE t.name LIKE :name AND u.status.id = :statusId")
    List<Task> findTasksByUserNameAndStatusId(@Param("name")String name,@Param("statusId") Long statusId);
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status  WHERE t.id = :id")
    void updateTaskStatus(@Param("id") Long id, @Param("status") Status status);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.category = :category  WHERE t.id = :id")
    void updateTaskCategory(@Param("id") Long id, @Param("category") Category category);

    void deleteByUserId(Long id);



}