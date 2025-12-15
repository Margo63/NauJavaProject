package ru.margarita.NauJava.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.domain.userData.UserDataServiceImpl;
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


    /**
     * метод для получения пользователя по имени
     * */
    @GetMapping("/findByName")
    public User findByName(@RequestParam String name) {
        return userService.findUserByName(name);
    }

    /**
     * метод для получения пользователя по почте и паролю
     * */
    @GetMapping("/findByEmailAndPassword")
    public List<User> findByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return userService.findByEmailAndPassword(email, password);
    }


    /**
     * метод для добавления нового пользователя
     * */
    @PostMapping("/addUser")
    public void addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        userService.createUser(name, email, password);
    }

    /**
     * метод для удаления пользователя по имени
     * */
    @Transactional
    @DeleteMapping("/delete")
    public String delete(@RequestParam String name) {
        taskService.deleteUserByName(name);
        return "ok";
    }

    /**
     * метод для изменения данных пользователя
     * */
    @Transactional
    @PutMapping("/update")
    public String update(Model model, String name, String email, String surname, String patronymic, String job) {
        User user = userService.findUserByName(name);
        userService.updateUserEmail(user.getId(), email);

        userDataService.updateUser(user.getId(), surname, patronymic, job);
        return "ok";
    }

    /**
     * метод для изменения ключевых данных пользователя
     * */
    @Transactional
    @PutMapping("/updateMainInfo")
    public String updateMainInfo(Model model, Long id, String name, String email, String password, Boolean isAdmin) {
        User oldUser = userService.findUserById(id);
        //имя не поменялось или уникально
        if (Objects.equals(oldUser.getName(), name) || userService.findUserByName(name) == null) {
            if (password != "") userService.updateMainInfo(id, name, email, passwordEncoder.encode(password), isAdmin);
            else userService.updateMainInfo(id, name, email, oldUser.getPassword(), isAdmin);
            return "ok";
        }
        return "invalid name";
    }
}
