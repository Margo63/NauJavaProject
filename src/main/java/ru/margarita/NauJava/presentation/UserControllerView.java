package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.category.CategoryServiceImpl;
import ru.margarita.NauJava.domain.notification.NotificationServiceImpl;
import ru.margarita.NauJava.domain.status.StatusServiceImpl;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.domain.userData.UserDataServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.entities.*;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private NotificationServiceImpl notificationService;


    @GetMapping("/user")
    public String getUserInfo(Model model, @RequestParam(name = "categoryId", required = false) Long categoryId,
                              @RequestParam(name = "statusId", required = false) Long statusId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        UserData data = userDataService.findUserDataById(user.getId());
        saveData(model, user, data);

        List<Task> tasks;
        if (categoryId != null && categoryId != -1 && statusId != null && statusId != -1) {
            tasks = taskService.findTasksByUserNameAndCategoryIdAndStatusId(auth.getName(), categoryId, statusId);
        }else if(categoryId != null && categoryId != -1){
            tasks = taskService.findTasksByUserNameAndCategoryId(auth.getName(), categoryId);
        }else if(statusId != null && statusId != -1){
            tasks = taskService.findTasksByUserNameAndStatusId(auth.getName(), statusId);
        } else {
            tasks = taskService.findTasksByUserName(auth.getName());
        }

        List<Notification> notifications = new ArrayList<>();
        for (Task task: tasks){
            notifications.addAll(notificationService.findByTaskId(task.getId()));
        }
        Date now = new Date();
        model.addAttribute("notifications", notifications.stream().filter(
                notification -> notification.getSendTime().before(now) ||
                notification.getSendTime().equals(now)).toList());

        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedStatusId", statusId);

        List<Status> statusList = statusService.getAllStatuses();
        model.addAttribute("statuses", statusList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);
        return "user";
    }

    @GetMapping("/filterCategoryAndStatus")
    public String filterCategoryAndStatus(@RequestParam(name = "selectFilterCategory", required = false) Long categoryId,
                                          @RequestParam(name = "selectFilterStatus", required = false) Long statusId) {
        if ((categoryId == null || categoryId == -1) && (statusId == null || statusId == -1)) {
            return "redirect:/custom/users/view/user";
        } else if (statusId == null || statusId == -1) {
            return "redirect:/custom/users/view/user?categoryId=" + categoryId;
        }else if (categoryId == null || categoryId == -1) {
            return "redirect:/custom/users/view/user?statusId=" + statusId;
        }
        return "redirect:/custom/users/view/user?categoryId=" + categoryId+"&statusId="+statusId;
    }


    private void saveData(Model model, User user, UserData data) {
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("surname", data.getSurname());
        model.addAttribute("patronymic", data.getPatronymic());
        model.addAttribute("job", data.getJob());
    }
}
