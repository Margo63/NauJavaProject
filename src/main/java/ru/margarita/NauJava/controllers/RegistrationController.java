package ru.margarita.NauJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.margarita.NauJava.data.repositories.UserRepository;
import ru.margarita.NauJava.entities.User;

/**
 * Класс контроллер для регистрации
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-11-2
 */
@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(String name, String password,String email, Model model) {
        if (userRepository.findByName(name).isEmpty()){
            User user = new User(name, email, passwordEncoder.encode(password));
            userRepository.save(user);
            return "redirect:/login";
        }else{
            model.addAttribute("errorMessage", "user exists");
            return "registration";
        }
    }
}
