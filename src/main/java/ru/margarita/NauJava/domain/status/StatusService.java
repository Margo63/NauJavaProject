package ru.margarita.NauJava.domain.status;

import ru.margarita.NauJava.entities.Status;

import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
public interface StatusService {

    /**
     * метод для получения всех статусов
     * */
    List<Status> getAllStatuses();
}
