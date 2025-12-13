package ru.margarita.NauJava.domain.task;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.Status;
import ru.margarita.NauJava.entities.StatusCodes;
import ru.margarita.NauJava.entities.User;
import ru.margarita.NauJava.repositories.StatusRepository;
import ru.margarita.NauJava.repositories.TaskRepository;
import ru.margarita.NauJava.repositories.UserDataRepository;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.entities.Task;

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


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserDataRepository userDataRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    @Override
    public void deleteUserByName(String name) {
        // удалить все задачи связанные с пользователем
        List<Task> tasks = taskRepository.findTasksByUserName(name);
        for (Task task : tasks) {
            taskRepository.delete(task);
        }
        User user = userRepository.findByName(name).getFirst();
        userDataRepository.deleteById(user.getId());
        // удалить пользователя
        userRepository.deleteByName(name);
    }

    @Override
    public List<Task> findTasksByUserName(String name) {
        return taskRepository.findTasksByUserName(name);
    }

    @Override
    public boolean createTask(String title, String description, User user) {
        Task task = new Task(description, title, user);
        Status status = statusRepository.findByCode(StatusCodes.PENDING);
        task.setStatus(status);
        taskRepository.save(task);
        return true;
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateTitle(Long id, String newTitle) {

        return false;
    }

    @Override
    public void updateStatus(Long id, Long statusId) {
        Status status = statusRepository.findById(statusId).get();
        taskRepository.updateTaskStatus(id, status);
    }

    @Override
    public List<Task> getAll() {
        return (List<Task>) taskRepository.findAll();
    }
}