package ru.margarita.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Класс контроллер для взаимодействия с бд через репозиторий
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-27
 */
@RestController
@RequestMapping("/custom/tasks")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private UserServiceImpl userService;


    /**
     * метод для получения всех задач пользователя по имени
     * */
    @GetMapping("/findTasksByUserName")
    public List<Task> findTasksByUserName(@RequestParam String name) {
        return taskService.findTasksByUserName(name);
    }

    /**
     * метод для добавления новой задачи
     * */
    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description,
                          @RequestParam Long categoryId, @RequestParam String dueDate) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dueDate);
        taskService.createTask(title, description, categoryId, date, user);
        return "ok";
    }

    /**
     * метод для обновления данных задачи
     * */
    @Transactional
    @PutMapping("/update")
    public void updateTask(@RequestParam Long id, @RequestParam String title, @RequestParam String description, @RequestParam Long categoryId,
                           @RequestParam Long statusId, @RequestParam String dueDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dueDate);
        taskService.updateTask(id, title, description, categoryId, statusId, date);
    }

    /**
     * метод для изменения таймера задачи
     * */
    @Transactional
    @PutMapping("/updateTimer")
    public void updateTimer(@RequestParam Long id, @RequestParam Integer timerValue) {
        System.out.println(id + " " + timerValue);
        taskService.updateTimerValue(id, timerValue);
    }

    /**
     * метод дляудаления задачи
     * */
    @DeleteMapping("/delete")
    public String deleteTask(@RequestParam Long id) {
        taskService.deleteById(id);
        return "ok";
    }

    /**
     * метод для изменения статуса
     * */
    @Transactional
    @PutMapping("/selectStatus")
    public String selectStatus(@RequestParam Long taskId, @RequestParam Long newStatusId) {

        taskService.updateStatus(taskId, newStatusId);
        return "ok";
    }

    /**
     * метод для изменения категории
     * */
    @Transactional
    @PutMapping("/selectCategory")
    public String selectCategory(@RequestParam Long taskId, @RequestParam Long newCategoryId) {
        taskService.updateCategory(taskId, newCategoryId);
        return "ok";
    }
}
