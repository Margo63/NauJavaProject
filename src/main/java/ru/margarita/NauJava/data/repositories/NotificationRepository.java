package ru.margarita.NauJava.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.Notification;

/**
 * Класс взаимодействия с таблицей уведомлений
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@RepositoryRestResource(path = "notifications")
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
