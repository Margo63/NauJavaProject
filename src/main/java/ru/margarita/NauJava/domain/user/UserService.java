package ru.margarita.NauJava.domain.user;

import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.User;

import java.util.List;

public interface UserService {

    boolean createUser(String name, String email, String password);
    User findUserById(Long id);
    User findUserByName(String name);
    boolean deleteById(Long id);

    @Transactional
    boolean updateUserEmail(Long id, String newEmail);

    List<User> getAllUsers();

    List<User> findByEmailAndPassword(String email, String password);
}
