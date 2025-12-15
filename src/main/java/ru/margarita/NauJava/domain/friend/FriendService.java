package ru.margarita.NauJava.domain.friend;

import ru.margarita.NauJava.entities.Friend;
import ru.margarita.NauJava.entities.FriendStatus;
import ru.margarita.NauJava.entities.User;

import java.util.List;

/**
 * Класс сервис для взаимодействия с бд через слой данных
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
public interface FriendService {

    /**
    * метод для отправления пригашения
    * */
    Friend inviteFriend(User user, User friendUser);
    /**
     * метод для создания новой записи о друге
     * */
    Friend createFriend(User user, User friendUser);

    /**
     * метод для поиска по id
     * */
    Friend findById(Long id);

    /**
     * метод для получения всех пользователей у кого пользователь с именем name в друзьях
     * */
    List<Friend> findFriendWithByName(String name);

    /**
     * метод удаления по id
     * */
    void deleteById(Long id);

    /**
     * метод для обновления статуса
     * */
    void updateStatus(Long id, FriendStatus status);

    /**
     * метод для отправления пригашения
     * */
    List<Friend> getAll();

    /**
     * метод удаление друга
     * */
    void deleteFriendByUserIdAndFriendId(Long userId, Long friendId);

    /**
     * метод для поиска всех друзей пользователя по имени
     * */
    List<Friend> findFriends(String name);

}
