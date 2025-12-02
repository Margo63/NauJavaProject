package ru.margarita.NauJava.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.data.repositories.UserRepository;
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
    private UserRepository userRepository;
    @GetMapping("/findByName")
    public List<User> findByName(@RequestParam String name)
    {
        return userRepository.findByName(name);
    }
    @GetMapping("/findByEmailAndPassword")
    public List<User> findByEmailAndPassword(@RequestParam String email, @RequestParam String password)
    {
        return userRepository.findByEmailAndPassword(email,password);
    }
    @PostMapping("/addUser")
    public void addUser(@RequestParam String name,@RequestParam String email, @RequestParam String password)
    {
        User user = new User(name,email,password);
        userRepository.save(user);
    }
    @Transactional
    @DeleteMapping("/deleteByName")
    public void deleteByName(@RequestParam String name)
    {
        userRepository.deleteByName(name);
    }
}
