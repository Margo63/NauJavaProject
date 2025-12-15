package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.entities.User;

import java.util.List;

@Controller
@RequestMapping("/admin/view")
public class AdminControllerView {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/mainPage")
    String getAdminPage(){
        return "admin";
    }
    @GetMapping("/list")
    public String userListView(Model model) {
        List<User> user = userService.getAllUsers();
        System.out.println(user.getFirst().getAdmin());
        model.addAttribute("users", user);
        return "userList";
    }
}
