package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.Constants;
import com.momchil.TU4ALL.dbo.FriendshipDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.model.AddFriendRequest;
import com.momchil.TU4ALL.model.BlockUserRequest;
import com.momchil.TU4ALL.model.FriendRequestAction;
import com.momchil.TU4ALL.service.FriendshipService;
import com.momchil.TU4ALL.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/friends")
public class FriendController {

    private FriendshipService friendshipService;
    private UserService userService;

    public FriendController(FriendshipService friendService, UserService userService) {
        this.friendshipService = friendService;
        this.userService = userService;
    }

    @GetMapping("/get-friends/{userId}")
    public ResponseEntity<List<UserDBO>> getFriendsOfUser(@PathVariable("userId") long userId) {
        try {
            List<UserDBO> friends = friendshipService.getFriendsOfUser(userId);
            return ResponseEntity.ok(friends);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-requests/{userId}")
    public ResponseEntity<List<UserDBO>> getRequestsOfUser(@PathVariable("userId") long userId) {
        try {
            List<UserDBO> friends = friendshipService.getRequestsOfUser(userId);
            return ResponseEntity.ok(friends);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-non-friends/{userId}")
    public ResponseEntity<List<UserDBO>> getNonFriendUsers(@PathVariable long userId) {
        try {
            List<UserDBO> nonFriendUsers = friendshipService.getUsersNotFriendsOfUser(userId);
            return ResponseEntity.ok(nonFriendUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/send-request/{userId}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable long userId, @RequestBody FriendRequestAction friendRequestAction) {
        try {
            friendshipService.sendFriendRequest(userId, friendRequestAction.getFriendUserId());
            return ResponseEntity.ok("Friend request sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send friend request.");
        }
    }

    @PutMapping("/accept-request/{userId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable long userId, @RequestBody FriendRequestAction friendRequestAction) {
        try {
            friendshipService.acceptFriendRequest(userId, friendRequestAction.getFriendUserId());
            return ResponseEntity.ok("Friend request accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to accept friend request.");
        }
    }

    //This method is also used for declining friend requests
    @PutMapping("/remove-friend/{userId}")
    public ResponseEntity<String> removeFriend(@PathVariable("userId") long userId, @RequestBody FriendRequestAction friendRequestAction) {
        try {
            friendshipService.removeFriend(userId,friendRequestAction.getFriendUserId());
            return ResponseEntity.ok("Friend removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove friend.");
        }
    }

    @PutMapping("/decline-request/{userId}")
    public ResponseEntity<String> declineFriendRequest(@PathVariable("userId") long userId, @RequestBody FriendRequestAction friendRequestAction) {
        try {
            friendshipService.removeFriend(userId, friendRequestAction.getFriendUserId());
            return ResponseEntity.ok("Friend request declined.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove friend.");
        }
    }

//todo TBD

//    @PostMapping("/block-user/{id}")
//    public ResponseEntity<?> blockUser(@PathVariable long id, @RequestBody BlockUserRequest blockUserRequest) {
//    }

}
