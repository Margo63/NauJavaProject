package ru.margarita.NauJava.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.User;
import ru.margarita.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean createUser(Long id, String name, String email, String password) {
        User user = new User(name, email, password);
        userRepository.save(user);
        return true;
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name).getFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    @Override
    public boolean updateUserEmail(Long id, String newEmail) {
        userRepository.updateUserEmail(id, newEmail);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

}
