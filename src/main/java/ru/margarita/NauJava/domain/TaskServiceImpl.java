package ru.margarita.NauJava.domain;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.margarita.NauJava.data.repositories.TaskRepository;
import ru.margarita.NauJava.data.repositories.UserRepository;
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

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void deleteUserByName(String name) {
        // удалить все задачи связанные с пользователем
        List<Task> tasks = taskRepository.findTasksByUserName(name);
        for (Task task : tasks) {
            taskRepository.delete(task);
        }
        // удалить пользователя
        userRepository.deleteByName(name);
    }

    @Override
    public boolean createTask(Long id, String title, String description) {
        return false;
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