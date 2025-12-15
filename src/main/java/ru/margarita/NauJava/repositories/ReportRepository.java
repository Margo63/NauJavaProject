package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.Report;

/**
 * Класс взаимодействия с таблицей отчетов
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-12-15
 */
@RepositoryRestResource(path = "reports")
public interface ReportRepository extends JpaRepository<Report, Long> {
}
