package ru.margarita.NauJava.domain.notification;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.domain.category.CategoryService;
import ru.margarita.NauJava.entities.Notification;
import ru.margarita.NauJava.repositories.NotificationRepository;

import java.util.List;

/**
 * Реализация {@link NotificationService}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
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
