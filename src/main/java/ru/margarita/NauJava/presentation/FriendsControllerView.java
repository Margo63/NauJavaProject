package ru.margarita.NauJava.presentation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.margarita.NauJava.domain.friend.FriendServiceImpl;
import ru.margarita.NauJava.domain.task.TaskServiceImpl;
import ru.margarita.NauJava.domain.user.UserServiceImpl;
import ru.margarita.NauJava.domain.userData.UserDataServiceImpl;
import ru.margarita.NauJava.entities.*;

import java.util.List;

@Controller
@RequestMapping(value = "/custom/friends", method = RequestMethod.GET)
public class FriendsControllerView {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FriendServiceImpl friendService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private UserDataServiceImpl userDataService;

    @GetMapping("/friendList")
    public String getFriends(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Friend> friends = friendService.findFriends(auth.getName());
        List<Friend> sended = friends.stream().filter(friend -> friend.getStatus()== FriendStatus.IN_PROGRESS).toList();
        List<Friend> accepted = friends.stream().filter(friend -> friend.getStatus()== FriendStatus.ACCEPT).toList();

        List<Friend> inviters = friendService.findFriendWithByName(auth.getName()).stream().filter(friend -> friend.getStatus()== FriendStatus.IN_PROGRESS).toList();

        model.addAttribute("inviters",inviters);
        model.addAttribute("sended",sended);
        model.addAttribute("accepted",accepted);
        return "friends";
    }

    @PostMapping("/add")
    public String addFriend(Model model, String friendName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        User friend = userService.findUserByName(friendName);
        if(friend!=null && user!=friend)
            friendService.inviteFriend(user, friend);

        return "redirect:/custom/friends/friendList";
    }

    @PostMapping("/reject")
    public String rejectFriend(Model model, Long id){
        friendService.updateStatus(id, FriendStatus.REJECT);
        return "redirect:/custom/friends/friendList";
    }

    @PostMapping("/accept")
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

    @PostMapping("/delete")
    public String delete(Model model, Long id){
        friendService.deleteById(id);
        return "redirect:/custom/friends/friendList";
    }

    @PostMapping("/deleteFriend")
    public String deleteFriend(Model model, Long id, Long friendId){
        System.out.println("in delete: "+id +" "+ friendId);
        friendService.deleteFriendByUserIdAndFriendId(id,friendId);
        return "redirect:/custom/friends/friendList";
    }
    @GetMapping("/view")
    public String viewFriend(Model model, Long friendId){
        User user = userService.findUserById(friendId);
        model.addAttribute("name",user.getName());
        model.addAttribute("email",user.getEmail());

        UserData data = userDataService.findUserDataById(friendId);
        model.addAttribute("job", data.getJob());
        model.addAttribute("surname", data.getSurname());
        model.addAttribute("patronymic", data.getPatronymic());

        Iterable<Task> tasks = taskService.findTasksByUserName(user.getName());
        model.addAttribute("tasks",tasks);

        return "userViewFriend";
    }
}
