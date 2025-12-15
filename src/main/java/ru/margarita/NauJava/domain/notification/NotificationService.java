package ru.margarita.NauJava.domain.notification;

import ru.margarita.NauJava.entities.Notification;

import java.util.List;

public interface NotificationService{
    List<Notification> findByTaskId(Long id);
}
