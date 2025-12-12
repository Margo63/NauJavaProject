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

@RepositoryRestResource(path = "friends")
public interface FriendRepository extends CrudRepository<Friend,Long> {

    @Query("SELECT u FROM Friend u JOIN u.user t WHERE t.name LIKE %:name%")
    List<Friend> findByName(String name);

    @Query("SELECT u FROM Friend u JOIN u.friendUser t WHERE t.name LIKE %:name%")
    List<Friend> findByFriendName(String name);

    @Modifying()
    @Transactional
    @Query("UPDATE Friend u SET u.status = :status  WHERE u.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") FriendStatus status);

    @Modifying()
    @Transactional
    @Query("DELETE FROM Friend f WHERE (f.user.id = :id AND f.friendUser.id = :friend_id) OR (f.user.id = :friend_id AND f.friendUser.id = :id)")
    void deleteByUserIdAndFriendId(@Param("id") Long userId,@Param("friend_id") Long friendId);
}
