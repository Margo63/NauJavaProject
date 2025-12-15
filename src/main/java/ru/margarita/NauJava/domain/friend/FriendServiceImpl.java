package ru.margarita.NauJava.domain.friend;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.margarita.NauJava.domain.category.CategoryService;
import ru.margarita.NauJava.entities.Friend;
import ru.margarita.NauJava.entities.FriendStatus;
import ru.margarita.NauJava.entities.User;
import ru.margarita.NauJava.repositories.FriendRepository;

import java.util.List;

/**
 * Реализация {@link FriendService}
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-12-15
 */
@Service
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Friend inviteFriend(User user, User friendUser) {
        if (!friendRepository.findByName(user.getName()).stream().filter(friend -> friend.getFriendUser() == friendUser
                && (friend.getStatus() == FriendStatus.ACCEPT || friend.getStatus() == FriendStatus.IN_PROGRESS)).toList().isEmpty())
            return null;
        List<Friend> invites = friendRepository.findByFriendName(user.getName());
        if (invites.stream().filter(friend -> friend.getUser() == friendUser
                && (friend.getStatus() == FriendStatus.ACCEPT || friend.getStatus() == FriendStatus.IN_PROGRESS)).toList().isEmpty()) {
            Friend friend = new Friend(user, friendUser, FriendStatus.IN_PROGRESS);
            friendRepository.save(friend);
            return friend;
        }
        return null;

    }

    @Override
    public Friend createFriend(User user, User friendUser) {
        Friend friend = new Friend(user, friendUser, FriendStatus.IN_PROGRESS);
        friendRepository.save(friend);
        return friend;
    }

    @Override
    public Friend findById(Long id) {
        return friendRepository.findById(id).get();
    }

    @Override
    public List<Friend> findFriendWithByName(String name) {
        return friendRepository.findByFriendName(name);
    }

    @Override
    public void deleteById(Long id) {
        friendRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateStatus(Long id, FriendStatus status) {
        friendRepository.updateStatus(id, status);
    }

    @Override
    public List<Friend> getAll() {
        return (List<Friend>) friendRepository.findAll();
    }


    @Transactional
    @Override
    public void deleteFriendByUserIdAndFriendId(Long userId, Long friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public List<Friend> findFriends(String name) {
        return friendRepository.findByName(name);
    }

}
