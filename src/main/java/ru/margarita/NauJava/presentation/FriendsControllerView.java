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
@RequestMapping(value = "/custom/friends/view", method = RequestMethod.GET)
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


    @GetMapping("/viewFriend")
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
