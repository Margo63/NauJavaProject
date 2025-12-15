package ru.margarita.NauJava.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.User;
import ru.margarita.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link UserService}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User findUserByName(String name) {
        List<User> users = userRepository.findByName(name);
        if(users.isEmpty()) return null;
        return users.getFirst();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUserEmail(Long id, String newEmail) {
        userRepository.updateUserEmail(id, newEmail);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Transactional
    @Override
    public void updateMainInfo(Long id, String name, String email, String password, Boolean isAdmin) {
        userRepository.updateMainInfo(id, name, email, password, isAdmin);
    }

}
