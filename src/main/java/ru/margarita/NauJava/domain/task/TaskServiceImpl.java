package ru.margarita.NauJava.domain.task;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.margarita.NauJava.entities.User;
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

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserDataRepository userDataRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
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
        return true;
    }

    @Override
    public Task findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean updateTitle(Long id, String newTitle) {
        return false;
    }

    @Override
    public List<Task> getAll() {
        return List.of();
    }
}