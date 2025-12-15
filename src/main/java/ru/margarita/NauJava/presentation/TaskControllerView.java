package ru.margarita.NauJava.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.margarita.NauJava.domain.category.CategoryServiceImpl;
import ru.margarita.NauJava.domain.status.StatusServiceImpl;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.entities.Category;
import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.entities.Task;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Класс контроллер для отображения задач
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
@Controller
@RequestMapping(value = "/custom/tasks/view", method = RequestMethod.GET)
public class TaskControllerView {

    @Autowired
    private StatusServiceImpl statusService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private TaskServiceImpl taskService;

    /**
     * страница создания задачи
     * */
    @GetMapping("/taskCreate")
    String createTask(Model model){
        List<Status> statusList = statusService.getAllStatuses();
        model.addAttribute("statuses", statusList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);

        return "createTask";
    }

    /**
     * страница изменения задачи
     * */
    @GetMapping("/taskEdit")
    String editTask(Model model, Long taskId){

        List<Status> statusList = statusService.getAllStatuses();
        model.addAttribute("statuses", statusList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);

        Task task = taskService.findById(taskId);
        model.addAttribute("task", task);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("date", formatter.format(task.getDueDate()));
        return "editTask";
    }

    /**
     * страница отображения задачи
     * */
    @GetMapping("/task")
    String getTask(Model model, Long taskId){
        Task task = taskService.findById(taskId);
        model.addAttribute("task", task);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("date", formatter.format(task.getDueDate()));
        return "task";
    }
}
