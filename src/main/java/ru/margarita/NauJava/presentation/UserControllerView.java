package ru.margarita.NauJava.presentation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.category.CategoryServiceImpl;
import ru.margarita.NauJava.domain.status.StatusService;
import ru.margarita.NauJava.domain.status.StatusServiceImpl;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.domain.userData.UserDataServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.entities.*;

import java.util.List;

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
    private UserServiceImpl userService;

    @Autowired
    private UserDataServiceImpl userDataService;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private StatusServiceImpl statusService;

    @Autowired
    private CategoryServiceImpl categoryService;


    @GetMapping("/list")
    public String userListView(Model model){
        Iterable<User> products = userService.getAllUsers();
        model.addAttribute("users",products);
        return "userList";
    }
    @GetMapping("/user")
    public String getUserInfo(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        UserData data = userDataService.findUserDataById(user.getId());
        saveData(model, user, data);
        Iterable<Task> tasks = taskService.findTasksByUserName(auth.getName());
        model.addAttribute("tasks",tasks);

        List<Status> statusList = statusService.getAllStatuses();
        model.addAttribute("statuses",statusList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories",categoryList);
        return "user";
    }

    @Transactional
    @PostMapping("/update")
    public String update(Model model,String name, String email, String surname, String patronymic, String job) {
        User user = userService.findUserByName(name);
        userService.updateUserEmail(user.getId(), email);

        userDataService.updateUser(user.getId(), surname, patronymic, job);
        saveData(model,
                userService.findUserById(user.getId()),
                userDataService.findUserDataById(user.getId()));
        return "user";
    }
    @Transactional
    @PostMapping("/delete")
    public String delete(@RequestParam String name) {
        taskService.deleteUserByName(name);
        return "redirect:/login";
    }

    private void saveData(Model model, User user, UserData data){
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("surname", data.getSurname());
        model.addAttribute("patronymic", data.getPatronymic());
        model.addAttribute("job", data.getJob());
    }
}
