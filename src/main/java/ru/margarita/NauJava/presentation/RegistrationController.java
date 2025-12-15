package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.domain.userData.UserDataServiceImpl;
import ru.margarita.NauJava.entities.UserData;
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
    private UserServiceImpl userService;

    @Autowired
    private UserDataServiceImpl userDataService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * отображение формы регистрации
     * */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * отображение формы авторизации
     * */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * добавление пользователя
     * */
    @PostMapping("/registration")
    public String addUser(String name, String password,String email, Model model) {
        model.addAttribute("nameValue", name);
        model.addAttribute("emailValue", email);
        if (!password.isEmpty() && !name.isEmpty() && !email.isEmpty() && userService.findUserByName(name)==null){

            User user = userService.createUser(name, email, passwordEncoder.encode(password));
            userDataService.createUserData(new UserData(user.getId()));
            return "redirect:/login";
        }else{
            String message = "";
            if(userService.findUserByName(name)!=null)
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
