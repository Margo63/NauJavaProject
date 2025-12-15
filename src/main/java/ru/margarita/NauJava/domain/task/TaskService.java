package ru.margarita.NauJava.domain.task;

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

    /**
     * метод для создания задачи
     * */
    void createTask(String title, String description, Long categoryId, Date dueDate, User user);
    /**
     * метод для поиска задачи по id
     * */
    Task findById(Long id);

    /**
     * метод удаления задачи по id
     * */
    void deleteById(Long id);

    /**
     * метод для обновления статуса задачи
     * */
    void updateStatus(Long id, Long statusId);

    /**
     * метод для обновления категории задачи
     * */
    void updateCategory(Long id, Long categoryId);

    /**
     * метод для получения всех задач
     * */
    List<Task> getAll();

    /**
     * метод для удаления пользователя и всех свзанных задач и напоминаний
     * */
    void deleteUserByName(String name);

    /**
     * метод для поиска задач по имени пользователя
     * */
    List<Task> findTasksByUserName(String name);

    /**
     * метод для поиска задач по имени пользователя и категории
     * */
    List<Task> findTasksByUserNameAndCategoryId(String name, Long categoryId);

    /**
     * метод для поиска задач по имени пользователя, статусу и категории
     * */
    List<Task> findTasksByUserNameAndCategoryIdAndStatusId(String name, Long categoryId, Long statusId);

    /**
     * метод для поиска задач по имени пользователя и статуса
     * */
    List<Task> findTasksByUserNameAndStatusId(String name, Long statusId);

    /**
     * метод для обновления информации о задаче
     * */

    void updateTask(Long id,String title, String description, Long categoryId,Long statusId, Date date);

    /**
     * метод для одновления таймера задачи
     * */
    void updateTimerValue(Long id, Integer timerValue);
}