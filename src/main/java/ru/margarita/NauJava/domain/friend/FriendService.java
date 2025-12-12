package ru.margarita.NauJava.domain.friend;

import ru.margarita.NauJava.entities.Friend;
import ru.margarita.NauJava.entities.FriendStatus;
import ru.margarita.NauJava.entities.User;

import java.util.List;

public interface FriendService {
    Friend inviteFriend(User user, User friendUser);
    Friend createFriend(User user, User friendUser);
    Friend findById(Long id);
    List<Friend> findFriendWithByName(String name);
    boolean deleteById(Long id);
    boolean deleteByName(String name);
    boolean updateStatus(Long id, FriendStatus status);
    List<Friend> getAll();
    void deleteFriendByUserIdAndFriendId(Long userId, Long friendId);

    List<Friend> findFriends(String name);

}
