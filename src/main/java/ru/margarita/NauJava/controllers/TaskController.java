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

import java.util.List;
import java.util.Optional;

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
    private TaskServiceImpl taskService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/findTasksByUserName")
    public List<Task> findTasksByUserName(@RequestParam String name){
        return  taskService.findTasksByUserName(name);
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(auth.getName()).getFirst();
        taskService.createTask(title, description, user);
        return "redirect:/custom/users/view/user";
    }
    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id){
        taskService.deleteById(id);
        return "redirect:/custom/users/view/user";
    }

    @Transactional
    @PostMapping("/selectStatus")
    public String selectStatus(@RequestParam Long taskId, @RequestParam Long newStatusId){
        taskService.updateStatus(taskId, newStatusId);
//        Optional<Task> task = taskRepository.findById(id);
//        taskRepository.delete(task.get());
        return "redirect:/custom/users/view/user";
    }

}
