package ru.margarita.NauJava.domain.notification;

import ru.margarita.NauJava.entities.Notification;

import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
public interface NotificationService{
    /**
     * метод для получения напоминия по задаче
     * */
    List<Notification> findByTaskId(Long id);
}
