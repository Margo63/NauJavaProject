package ru.margarita.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.User;
import java.util.List;

/**
 * Класс взаимодействия с таблицей пользователей
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long>
{
    List<User> findByName(String name);
    List<User> findByEmailAndPassword(String email, String password);
    void deleteByName(String name);
}