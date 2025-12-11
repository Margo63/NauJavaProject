package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.repositories.TaskRepository;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

/**
 * Класс для отображения списка пользователей
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@Controller
@RequestMapping(value = "/custom/users/view", method = RequestMethod.GET)
public class UserControllerView {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/list")
    public String userListView(Model model){
        Iterable<User> products = userRepository.findAll();
        model.addAttribute("users",products);
        return "userList";
    }
    @GetMapping("/user")
    public String getUserInfo(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
        Iterable<Task> tasks = taskRepository.findTasksByUserName(auth.getName());
        model.addAttribute("tasks",tasks);
        return "user";
    }


}
