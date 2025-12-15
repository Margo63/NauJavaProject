package ru.margarita.NauJava.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Objects;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return "ok";
    }

    @Transactional
    @PutMapping("/update")
    public String update(Model model, String name, String email, String surname, String patronymic, String job) {
        User user = userService.findUserByName(name);
        userService.updateUserEmail(user.getId(), email);

        userDataService.updateUser(user.getId(), surname, patronymic, job);
        return "ok";
    }

    @Transactional
    @PutMapping("/updateMainInfo")
    public String updateMainInfo(Model model,Long id, String name, String email, String password, Boolean isAdmin) {
        User oldUser = userService.findUserById(id);
        System.out.println("///////////old user"+oldUser.getName()+" "+name);
        //имя не поменялось или уникально
        if(Objects.equals(oldUser.getName(), name) || userService.findUserByName(name)==null){
            System.out.println("/////in condition");
            if(password!="")
                userService.updateMainInfo(id, name, email, passwordEncoder.encode(password), isAdmin);
            else{
                System.out.println("//////////"+isAdmin);
                userService.updateMainInfo(id, name, email, oldUser.getPassword(), isAdmin);
            }

            return "ok";
        }
        return "invalid name";
    }

//    @Transactional
//    @DeleteMapping("/deleteByName")
//    public void deleteByName(@RequestParam String name) {
//        List<Task> tasks = taskRepository.findTasksByUserName(name);
//        taskRepository.deleteAll(tasks);
//        userRepository.deleteByName(name);
//    }
}
