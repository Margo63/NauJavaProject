package ru.margarita.NauJava.domain.user;

import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.User;

import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
public interface UserService {

    /**
     * метод для создания пользователя
     * */
    User createUser(String name, String email, String password);

    /**
     * метод для поиска пользователя по id
     * */
    User findUserById(Long id);

    /**
     * метод для поиска пользователя по имени
     * */
    User findUserByName(String name);

    /**
     * метод для удаления пользователя по id
     * */
    void deleteById(Long id);

    /**
     * метод для обновления почты пользователя
     * */
    void updateUserEmail(Long id, String newEmail);

    /**
     * метод для получения всех пользователей
     * */
    List<User> getAllUsers();

    /**
     * метод для получения пользователя по почте и паролю
     * */
    List<User> findByEmailAndPassword(String email, String password);

    /**
     * метод для обновления важной информации пользователя
     * */
    void updateMainInfo(Long id, String name, String email, String password, Boolean isAdmin);
}
