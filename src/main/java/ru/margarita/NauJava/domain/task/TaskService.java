package ru.margarita.NauJava.domain.task;

import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

import java.util.Date;
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
    boolean createTask(String title, String description, Long categoryId, Date dueDate, User user);
    Task findById(Long id);
    boolean deleteById(Long id);
    boolean updateTitle(Long id, String newTitle);

    void updateStatus(Long id, Long statusId);
    void updateCategory(Long id, Long categoryId);
    List<Task> getAll();
    void deleteUserByName(String name);

    List<Task> findTasksByUserName(String name);

    List<Task> findTasksByUserNameAndCategoryId(String name, Long categoryId);

    List<Task> findTasksByUserNameAndCategoryIdAndStatusId(String name, Long categoryId, Long statusId);

    List<Task> findTasksByUserNameAndStatusId(String name, Long statusId);
}