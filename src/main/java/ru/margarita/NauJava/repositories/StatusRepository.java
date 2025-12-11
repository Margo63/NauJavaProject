package ru.margarita.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.Status;

/**
 * Класс взаимодействия с таблицей статусов
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-10-27
 */
@RepositoryRestResource(path = "statuses")
public interface StatusRepository extends CrudRepository<Status, Long> {
}
