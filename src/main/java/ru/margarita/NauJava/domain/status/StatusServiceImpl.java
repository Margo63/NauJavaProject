package ru.margarita.NauJava.domain.status;

import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.repositories.StatusRepository;

import java.util.List;

/**
 * Реализация {@link StatusService}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
@Service
public class StatusServiceImpl implements StatusService{
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }
    @Override
    public List<Status> getAllStatuses() {
        return (List<Status>) statusRepository.findAll();
    }
}
