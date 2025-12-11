package ru.margarita.NauJava.tests;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.margarita.NauJava.data.criteria_repo.TaskRepositoryCustom;
import ru.margarita.NauJava.data.criteria_repo.UserRepositoryCustom;
import ru.margarita.NauJava.repositories.TaskRepository;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.domain.TaskService;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

@SpringBootTest
class UserTest {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final TaskRepositoryCustom taskRepositoryCustom;
    public final TaskRepository taskRepository;
    public final TaskService taskService;

    @Autowired
    UserTest(UserRepository userRepository, UserRepositoryCustom userRepositoryCustom, TaskRepositoryCustom taskRepositoryCustom, TaskRepository taskRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.userRepositoryCustom = userRepositoryCustom;
        this.taskRepositoryCustom = taskRepositoryCustom;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    /**
     * Тестирование поиск пользователя по его имени
     * положительный
     */
    @Test
    void testFindUserByNamePositive() {
        //создание пользователя
        User user = createUserRandomName();
        // поиск пользователя по имени
        User foundUser = userRepository.findByName(user.getName()).getFirst();
        // проверки
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(user.getName(), foundUser.getName());
    }

    /**
     * Тестирование поиск пользователя по его имени
     * отрицательный
     */
    @Test
    void testFindUserByNameNegative() {
        //поиск не существующего пользователя
        List<User> foundUser = userRepository.findByName("Not John");
        //проверка
        Assertions.assertTrue(foundUser.isEmpty());
    }

    /**
     * Тестирование поиск пользователя по почте или паролю
     * положительный
     */
    @Test
    void testFindByEmailAndPasswordPositive() {
        //создание пользователя
        String email = "mail@mail.com";
        String password = "1111";
        User user = createUserWithEmailAndPassword(email, password);

        //поиск по почте и паролю
        User foundUser = userRepository.findByEmailAndPassword(email, password).getFirst();
        //проверка
        Assertions.assertEquals(user.getEmail(), foundUser.getEmail());
    }

    /**
     * Тестирование поиск пользователя по почте или паролю
     * отрицательный
     */
    @Test
    void testFindByEmailAndPasswordNegative() {
        //создание пользователя
        String email = "mail";
        String password = "222";
        createUserWithEmailAndPassword(email, password);
        //поиск
        List<User> foundUser = userRepository.findByEmailAndPassword("not email", "222");
        //проверка
        Assertions.assertTrue(foundUser.isEmpty());
    }

    /**
     * Тестирование поиск задач по имени пользователя
     * положительный
     */
    @Test
    void testFindTasksByUserNamePositive() {
        //создание пользователя с задачей
        User user = createUserRandomName();
        Task task = createTask(user, "task1");
        //поиск задач по имени пользователя
        Task foundTask = taskRepository.findTasksByUserName(user.getName()).getFirst();
        //проверка
        Assertions.assertEquals(task.getTitle(), foundTask.getTitle());
    }

    /**
     * Тестирование поиск задач по имени пользователя
     * положительный
     */
    @Test
    void testFindTasksByUserNameNegative() {
        //создание пользователя
        User user = createUserRandomName();
        //поиск задач по имени
        List<Task> foundTask = taskRepository.findTasksByUserName(user.getName());
        //проверка
        Assertions.assertTrue(foundTask.isEmpty());
    }

    /**
     * Тестирование функций реализованных с помощью CriteriaApi
     */
    @Test
    void testCriteria() {
        //создание пользователя и задачи
        User user = createUserRandomName();
        Task task = createTask(user, "taskCriteria");

        //поиск с существущими параметрами
        User foundUserName = userRepositoryCustom.findByName(user.getName()).getFirst();
        User foundUserEmail = userRepositoryCustom.findByEmailAndPassword(user.getEmail(), user.getPassword()).getFirst();
        Task foundTask = taskRepositoryCustom.findTasksByUserName(user.getName()).getFirst();

        //поиск по несуществующем параметрам
        List<User> foundUserNameNeg = userRepositoryCustom.findByName("");
        List<User> foundUserEmailNeg = userRepositoryCustom.findByEmailAndPassword(user.getEmail(), "");
        List<Task> foundTaskNeg = taskRepositoryCustom.findTasksByUserName("NOT EXIST");

        //положительная проверка
        Assertions.assertEquals(user.getName(), foundUserName.getName());
        Assertions.assertEquals(user.getEmail(), foundUserEmail.getEmail());
        Assertions.assertEquals(task.getTitle(), foundTask.getTitle());

        //отрицательная проверка
        Assertions.assertTrue(foundUserNameNeg.isEmpty());
        Assertions.assertTrue(foundUserEmailNeg.isEmpty());
        Assertions.assertTrue(foundTaskNeg.isEmpty());
    }

    /**
     * Тестирование удаления задач при удалении пользователя
     */
    @Test
    void testDelete() {
        //создание пользователя и задач
        User user = createUserRandomName();
        Task task1 = createTask(user, "delete1");
        Task task2 = createTask(user, "delete2");

        User userD = createUserRandomName();
        Task taskD1 = createTask(userD, "deleteD1");
        Task taskD2 = createTask(userD, "deleteD2");

        //удаление пользователя
        taskService.deleteUserByName(user.getName());
        //попытка удаления не существующего пользователя
        taskService.deleteUserByName(user.getName());

        //поиск удаленного пользователя и задач
        Optional<User> foundUser = userRepository.findById(user.getId());
        Optional<Task> foundTask1 = taskRepository.findById(task1.getId());
        Optional<Task> foundTask2 = taskRepository.findById(task2.getId());

        //поиск не удаленного пользователя и задач
        Optional<User> foundUserD = userRepository.findById(userD.getId());
        Optional<Task> foundTaskD1 = taskRepository.findById(taskD1.getId());
        Optional<Task> foundTaskD2 = taskRepository.findById(taskD2.getId());

        //проверка что пользователь и задачи удалены
        Assertions.assertTrue(foundUser.isEmpty());
        Assertions.assertTrue(foundTask1.isEmpty());
        Assertions.assertTrue(foundTask2.isEmpty());

        //проверка что пользователь и задачи не удалены
        Assertions.assertFalse(foundUserD.isEmpty());
        Assertions.assertFalse(foundTaskD1.isEmpty());
        Assertions.assertFalse(foundTaskD2.isEmpty());
    }

    private User createUserRandomName() {
        // генерация имени пользователя
        String userName = UUID.randomUUID().toString();
        // создание пользователя
        User user = new User(userName, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }

    private User createUserWithEmailAndPassword(String email, String password) {
        // генерация имени пользователя
        String userName = UUID.randomUUID().toString();
        // создание пользователя
        User user = new User(userName, email, password);
        userRepository.save(user);
        return user;
    }

    private Task createTask(User user, String title) {
        Task task1 = new Task(title, user);
        taskRepository.save(task1);
        return task1;
    }
}