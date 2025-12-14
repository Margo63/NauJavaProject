package ru.margarita.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.status.StatusServiceImpl;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.entities.StatusCodes;
import ru.margarita.NauJava.repositories.TaskRepository;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private UserRepository userRepository;


    @GetMapping("/findTasksByUserName")
    public List<Task> findTasksByUserName(@RequestParam String name){
        return  taskService.findTasksByUserName(name);
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description,
                          @RequestParam Long categoryId, @RequestParam String dueDate) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(auth.getName()).getFirst();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dueDate);
        taskService.createTask(title, description,categoryId,date, user);
        return "redirect:/custom/users/view/user";
    }
    @DeleteMapping("/delete")
    public String deleteTask(@RequestParam Long id){
        taskService.deleteById(id);
        return "redirect:/custom/users/view/user";
    }

    @Transactional
    @PutMapping("/selectStatus")
    public String selectStatus(@RequestParam Long taskId, @RequestParam Long newStatusId){

        taskService.updateStatus(taskId, newStatusId);
        return "redirect:/custom/users/view/user";
    }

    @Transactional
    @PutMapping("/selectCategory")
    public String selectCategory(@RequestParam Long taskId, @RequestParam Long newCategoryId){
        taskService.updateCategory(taskId, newCategoryId);
        return "redirect:/custom/users/view/user";
    }
}
