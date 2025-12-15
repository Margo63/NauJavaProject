package ru.margarita.NauJava.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.task.TaskService;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.domain.userData.UserDataServiceImpl;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.repositories.TaskRepository;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.entities.User;

import java.util.List;

/**
 * Класс для взаимодействия с бд через репозиторий
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-27
 */
@RestController
@RequestMapping("/custom/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private UserDataServiceImpl userDataService;

    @GetMapping("/findByName")
    public User findByName(@RequestParam String name) {
        return userService.findUserByName(name);
    }

    @GetMapping("/findByEmailAndPassword")
    public List<User> findByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return userService.findByEmailAndPassword(email, password);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        userService.createUser(name, email, password);
    }

    @Transactional
    @DeleteMapping("/delete")
    public String delete(@RequestParam String name) {
        taskService.deleteUserByName(name);
        return "redirect:/login";
    }

    @Transactional
    @PutMapping("/update")
    public String update(Model model, String name, String email, String surname, String patronymic, String job) {
        User user = userService.findUserByName(name);
        userService.updateUserEmail(user.getId(), email);

        userDataService.updateUser(user.getId(), surname, patronymic, job);
        return "redirect:/custom/users/view/user";
    }

//    @Transactional
//    @DeleteMapping("/deleteByName")
//    public void deleteByName(@RequestParam String name) {
//        List<Task> tasks = taskRepository.findTasksByUserName(name);
//        taskRepository.deleteAll(tasks);
//        userRepository.deleteByName(name);
//    }
}
