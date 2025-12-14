package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.margarita.NauJava.domain.category.CategoryService;
import ru.margarita.NauJava.domain.category.CategoryServiceImpl;
import ru.margarita.NauJava.domain.status.StatusService;
import ru.margarita.NauJava.domain.status.StatusServiceImpl;
import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.entities.Status;

import java.util.List;

@Controller
@RequestMapping(value = "/custom/tasks/view", method = RequestMethod.GET)
public class TaskControllerView {

    @Autowired
    private StatusServiceImpl statusService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/task")
    String createTask(Model model){
        List<Status> statusList = statusService.getAllStatuses();
        model.addAttribute("statuses", statusList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);

        return "createTask";
    }
}
