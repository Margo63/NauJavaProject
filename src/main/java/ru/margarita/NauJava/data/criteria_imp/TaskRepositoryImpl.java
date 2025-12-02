package ru.margarita.NauJava.data.criteria_imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.margarita.NauJava.data.criteria_repo.TaskRepositoryCustom;
import ru.margarita.NauJava.entities.Task;
import ru.margarita.NauJava.entities.User;

import java.util.List;

/**
 * Реализация {@link TaskRepositoryCustom}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-21
 */
@Repository
public class TaskRepositoryImpl implements TaskRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Task> findTasksByUserName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteriaQuery.from(Task.class);

        Join<Task, User> role = taskRoot.join("user", JoinType.INNER);
        Predicate namePredicate = criteriaBuilder.equal(role.get("name"), name);
        criteriaQuery.select(taskRoot).where(namePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
