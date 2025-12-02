package ru.margarita.NauJava.data.criteria_repo;

import ru.margarita.NauJava.entities.User;
import java.util.List;
/**
 * интерфейс для работы с бд
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-21
 */
public interface UserRepositoryCustom {
    List<User> findByName(String name);
    List<User> findByEmailAndPassword(String email, String password);
}