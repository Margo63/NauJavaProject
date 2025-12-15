package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.margarita.NauJava.entities.UserData;
import ru.margarita.NauJava.repositories.UserDataRepository;
import ru.margarita.NauJava.repositories.UserRepository;
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
    private UserDataRepository userDataRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String addUser(String name, String password,String email, Model model) {
        model.addAttribute("nameValue", name);
        model.addAttribute("emailValue", email);
        if (!password.isEmpty() && !name.isEmpty() && !email.isEmpty() && userRepository.findByName(name).isEmpty()){
            User user = new User(name, email, passwordEncoder.encode(password));
            userRepository.save(user);
            UserData data = new UserData(user.getId());
            userDataRepository.save(data);
            return "redirect:/login";
        }else{
            String message = "";
            if(!userRepository.findByName(name).isEmpty())
                message+="users exists\n";
            else{
                if(name.isEmpty())
                    message+="name not correct\n";
                if(password.isEmpty())
                    message+="password not correct\n";
                if(email.isEmpty())
                    message+="email not correct\n";
            }
            model.addAttribute("errorMessage", message);
            return "registration";
        }
    }
}
