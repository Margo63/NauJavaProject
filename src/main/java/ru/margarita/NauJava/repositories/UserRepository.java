package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
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

    @Modifying()
    @Transactional
    @Query("UPDATE User u SET u.email = :email, u.password = :password WHERE u.id = :id")
    void updateUserEmailAndPassword(@Param("id") Long id,@Param("email") String email,@Param("password") String password);
    void deleteByName(String name);
}