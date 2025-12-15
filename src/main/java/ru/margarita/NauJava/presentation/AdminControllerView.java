package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.entities.User;

@Controller
@RequestMapping("/admin/view")
public class AdminControllerView {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/mainPage")
    String getAdminPage(){
        return "admin";
    }
    @GetMapping("/list")
    public String userListView(Model model) {
        Iterable<User> products = userService.getAllUsers();
        model.addAttribute("users", products);
        return "userList";
    }
}
