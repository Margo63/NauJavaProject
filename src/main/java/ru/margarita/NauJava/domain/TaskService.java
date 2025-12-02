package ru.margarita.NauJava.domain;

import ru.margarita.NauJava.entities.Task;
import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-21
 */
public interface TaskService
{
    boolean createTask(Long id, String title, String description);
    Task findById(Long id);
    boolean deleteById(Long id);
    boolean updateTitle(Long id, String newTitle);
    List<Task> getAll();
    void deleteUserByName(String name);
}