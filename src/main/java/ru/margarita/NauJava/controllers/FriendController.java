package ru.margarita.NauJava.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.margarita.NauJava.domain.friend.FriendServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.entities.Friend;
import ru.margarita.NauJava.entities.FriendStatus;
import ru.margarita.NauJava.entities.User;

@RestController
@RequestMapping("custom/friends")
public class FriendController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FriendServiceImpl friendService;

    @PostMapping("/add")
    public String addFriend(Model model, String friendName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        User friend = userService.findUserByName(friendName);
        if(friend!=null && user!=friend)
            friendService.inviteFriend(user, friend);

        return "redirect:/custom/friends/friendList";
    }

    @Transactional
    @PutMapping("/reject")
    public String rejectFriend(Model model, Long id){
        friendService.updateStatus(id, FriendStatus.REJECT);
        return "redirect:/custom/friends/friendList";
    }

    @Transactional
    @PutMapping("/accept")
    public String acceptFriend(Long id, Long userInviterId, Long userId){
        User userInviter = userService.findUserById(userInviterId);
        User user = userService.findUserById(userId);
        if(userInviter!=null && user!=null){
            Friend friend = friendService.createFriend(user, userInviter);

            friendService.updateStatus(friend.getId(), FriendStatus.ACCEPT);
            friendService.updateStatus(id, FriendStatus.ACCEPT);
        }

        return "redirect:/custom/friends/friendList";
    }

    @Transactional
    @DeleteMapping("/delete")
    public String delete(Model model, Long id){
        friendService.deleteById(id);
        return "redirect:/custom/friends/friendList";
    }

    @Transactional
    @DeleteMapping("/deleteFriend")
    public String deleteFriend(Model model, Long id, Long friendId){
        friendService.deleteFriendByUserIdAndFriendId(id,friendId);
        return "redirect:/custom/friends/friendList";
    }
}
