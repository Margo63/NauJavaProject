package ru.margarita.NauJava.domain.task;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.*;
import ru.margarita.NauJava.repositories.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Реализация {@link TaskService}
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-31
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final StatusRepository statusRepository;

    private final NotificationRepository notificationRepository;

    private final FriendRepository friendRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserDataRepository userDataRepository, StatusRepository statusRepository, NotificationRepository notificationRepository, FriendRepository friendRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.statusRepository = statusRepository;
        this.notificationRepository = notificationRepository;
        this.friendRepository = friendRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void deleteUserByName(String name) {
        // удалить все задачи связанные с пользователем
        List<Task> tasks = taskRepository.findTasksByUserName(name);
        for (Task task : tasks) {
            notificationRepository.deleteByTaskId(task.getId());
            taskRepository.delete(task);
        }
        User user = userRepository.findByName(name).getFirst();
        userDataRepository.deleteById(user.getId());

        //удаление всех друзей
        friendRepository.deleteByUserId(user.getId());


        // удалить пользователя
        userRepository.deleteByName(name);
    }

    @Override
    public List<Task> findTasksByUserName(String name) {
        return taskRepository.findTasksByUserName(name);
    }

    @Override
    public List<Task> findTasksByUserNameAndCategoryId(String name, Long categoryId) {
        return taskRepository.findTasksByUserNameAndCategoryId(name, categoryId);
    }

    @Override
    public List<Task> findTasksByUserNameAndCategoryIdAndStatusId(String name, Long categoryId, Long statusId) {
        return taskRepository.findTasksByUserNameAndCategoryIdAndStatusId(name, categoryId, statusId);
    }

    @Override
    public List<Task> findTasksByUserNameAndStatusId(String name, Long statusId) {
        return taskRepository.findTasksByUserNameAndStatusId(name, statusId);
    }

    @Override
    public void updateTask(Long id, String title, String description, Long categoryId, Long statusId, Date date) {
        Status status = statusRepository.findById(statusId).get();
        Category category = categoryRepository.findById(categoryId).get();
        notificationRepository.deleteByTaskId(id);
        taskRepository.updateTask(id, title, description, category, status, date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        Date sendDate = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        notificationRepository.save(new Notification("deadline of task "+title+"is "+ sdf.format(date),
                sendDate,  taskRepository.findById(id).get()));
    }

    @Override
    public void updateTimerValue(Long id, Integer timerValue) {
        System.out.println(id+" "+timerValue);
        taskRepository.updateTimerValue(id, timerValue);
    }

    @Override
    public void createTask(String title, String description, Long categoryId, Date dueDate, User user) {
        Task task = new Task(description, title, user);
        Status status = statusRepository.findByCode(StatusCodes.PENDING);
        task.setStatus(status);
        Category category = categoryRepository.findById(categoryId).get();
        task.setCategory(category);
        task.setDueDate(dueDate);
        task.setTimerValue(30);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        cal.add(Calendar.DATE, -1);
        Date sendDate = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        taskRepository.save(task);
        notificationRepository.save(new Notification("deadline of task "+task.getTitle()+"is "+ sdf.format(dueDate), sendDate, task));
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteByTaskId(id);
        taskRepository.deleteById(id);
    }


    @Override
    public void updateStatus(Long id, Long statusId) {
        Status status = statusRepository.findById(statusId).get();
        taskRepository.updateTaskStatus(id, status);
    }

    @Override
    public void updateCategory(Long id, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        taskRepository.updateTaskCategory(id, category);
    }

    @Override
    public List<Task> getAll() {
        return (List<Task>) taskRepository.findAll();
    }
}