package ru.margarita.NauJava.repositories;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.entities.Friend;
import ru.margarita.NauJava.entities.FriendStatus;

import java.util.List;

/**
 * Класс взаимодействия с таблицей друзей
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-12-15
 */
@RepositoryRestResource(path = "friends")
public interface FriendRepository extends CrudRepository<Friend,Long> {

    /**
     * поиск по имени
     * */
    @Query("SELECT u FROM Friend u JOIN u.user t WHERE t.name LIKE %:name%")
    List<Friend> findByName(String name);


    /**
     * поиск по имени друга
     * */
    @Query("SELECT u FROM Friend u JOIN u.friendUser t WHERE t.name LIKE %:name%")
    List<Friend> findByFriendName(String name);

    /**
     * поиск по статусу
     * */
    @Modifying()
    @Transactional
    @Query("UPDATE Friend u SET u.status = :status  WHERE u.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") FriendStatus status);

    /**
     * удаление пары друзей
     * */
    @Modifying()
    @Transactional
    @Query("DELETE FROM Friend f WHERE (f.user.id = :id AND f.friendUser.id = :friend_id) OR (f.user.id = :friend_id AND f.friendUser.id = :id)")
    void deleteByUserIdAndFriendId(@Param("id") Long userId,@Param("friend_id") Long friendId);

    /**
     * удаление по id пользователя
     * */
    @Modifying()
    @Transactional
    @Query("DELETE FROM Friend f WHERE f.user.id = :id OR f.friendUser.id = :id")
    void deleteByUserId(@Param("id") Long id);
}
