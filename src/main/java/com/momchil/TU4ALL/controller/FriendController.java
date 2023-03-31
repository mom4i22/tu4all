package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.FriendDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.FriendRepository;
import com.momchil.TU4ALL.service.FriendService;
import com.momchil.TU4ALL.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/friends")
public class FriendController {

    private FriendService friendService;
    private UserService userService;

    public FriendController(FriendService friendService, UserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @GetMapping("/get-friends/{id}")
    public ResponseEntity<List<FriendDBO>> getAllFriends(@PathVariable long id) {
        UserDBO userDBO = userService.readById(id);
        List<FriendDBO> friendDBOS = userDBO.getFriends();
        return ResponseEntity.ok(friendDBOS);
    }

    @PostMapping("/add-friend/{id}")
    public ResponseEntity<?> addFriend(@PathVariable long id, @RequestBody FriendDBO friendDBO) {
        friendService.addFriend(friendDBO);
        UserDBO userDBO = userService.readById(id);
        List<FriendDBO> friends = userDBO.getFriends();
        friends.add(friendDBO);
        userDBO.setFriends(friends);
        userService.editUser(id,userDBO);

        return ResponseEntity.ok("Friend request sent");
    }

}