package ru.margarita.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.data.repositories.TaskRepository;
import ru.margarita.NauJava.data.repositories.UserRepository;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

import java.util.List;

/**
 * Класс контроллер для взаимодействия с бд через репозиторий
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-27
 */
@Controller
@RequestMapping("/custom/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findTasksByUserName")
    public List<Task> findTasksByUserName(@RequestParam String name){
        return  taskRepository.findTasksByUserName(name);
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(auth.getName()).getFirst();
        Task task = new Task(description,title,user);
        taskRepository.save(task);
        return "redirect:/custom/users/view/user";
    }
}
