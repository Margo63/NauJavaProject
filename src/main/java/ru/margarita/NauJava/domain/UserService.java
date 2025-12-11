package ru.margarita.NauJava.domain;

import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.User;

import java.util.List;

public interface UserService {

    boolean createUser(Long id, String name, String email, String password);
    User findUserById(Long id);
    User findUserByName(String name);
    boolean deleteById(Long id);

    @Transactional
    boolean updateUserEmail(Long id, String newEmail);

    List<User> getAllUsers();
}
