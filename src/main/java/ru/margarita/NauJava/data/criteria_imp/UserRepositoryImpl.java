package ru.margarita.NauJava.data.criteria_imp;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.margarita.NauJava.data.criteria_repo.UserRepositoryCustom;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import ru.margarita.NauJava.entities.User;

/**
 * Реализация {@link UserRepositoryCustom}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-21
 */

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;
    @Autowired
    public UserRepositoryImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    @Override
    public List<User> findByName(String name)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate namePredicate = criteriaBuilder.equal(userRoot.get("name"), name);
        criteriaQuery.select(userRoot).where(namePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<User> findByEmailAndPassword(String email, String password) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate emailPredicate = criteriaBuilder.equal(userRoot.get("email"), email);
        Predicate passwordPredicate = criteriaBuilder.equal(userRoot.get("password"), password);
        Predicate finalPredicate = criteriaBuilder.and(emailPredicate, passwordPredicate);
        criteriaQuery.select(userRoot).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
