package ru.margarita.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.UserData;

import java.util.Optional;

/**
 * Класс взаимодействия с таблицей информации о пользователях
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-12-10
 */
@RepositoryRestResource(path = "users_data")
public interface UserDataRepository extends CrudRepository<UserData, Long> {
    Optional<UserData> findById(Long id);

}
