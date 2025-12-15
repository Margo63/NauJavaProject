package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
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

    @Modifying()
    @Transactional
    @Query("UPDATE UserData u SET u.surname = :surname, u.patronymic = :patronymic, u.job = :job  WHERE u.id = :id")
    void updateUserData(@Param("id") Long id, @Param("surname") String surname,
                                    @Param("patronymic") String patronymic, @Param("job") String job);
    void deleteById(Long id);

}
