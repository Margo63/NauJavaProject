package ru.margarita.NauJava.data.criteria_repo;

import ru.margarita.NauJava.entities.Task;
import java.util.List;

/**
 * Интерфейс для работы с бд
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-21
 */
public interface TaskRepositoryCustom {
    List<Task> findTasksByUserName(String name);
}