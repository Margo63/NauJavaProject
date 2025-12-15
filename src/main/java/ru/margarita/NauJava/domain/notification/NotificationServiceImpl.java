package ru.margarita.NauJava.domain.notification;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.Notification;
import ru.margarita.NauJava.repositories.NotificationRepository;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> findByTaskId(Long id) {
        return notificationRepository.findByTaskId(id);
    }
}
