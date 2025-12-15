package ru.margarita.NauJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.margarita.NauJava.entities.Report;

@RepositoryRestResource(path = "reports")
public interface ReportRepository extends JpaRepository<Report, Long> {
}
