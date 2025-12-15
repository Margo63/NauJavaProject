package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.Notification;

import java.util.List;

/**
 * Класс взаимодействия с таблицей уведомлений
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@RepositoryRestResource(path = "notifications")
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    /**
     * удаление напоминания по id задачи
     * */
    @Modifying()
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.task.id = :id")
    void deleteByTaskId(@Param("id") Long id);

    /**
     * поиск напоминания по id задачи
     * */
    @Query("SELECT n FROM Notification n WHERE n.task.id = :id")
    List<Notification> findByTaskId(@Param("id")Long id);
}
