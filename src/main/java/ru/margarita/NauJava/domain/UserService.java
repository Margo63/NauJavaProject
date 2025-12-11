package ru.margarita.NauJava.domain;

import ru.margarita.NauJava.entities.User;

import java.util.List;

public interface UserService {

    boolean createUser(Long id, String name, String email, String password);
    User findUserById(Long id);
    boolean deleteById(Long id);
    boolean updateUser(Long id, String newEmail, String newPassword);
    List<User> getAllUsers();
}
