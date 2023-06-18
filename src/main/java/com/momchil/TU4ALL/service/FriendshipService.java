package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.Constants;
import com.momchil.TU4ALL.dbo.FriendshipDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.FriendshipRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(FriendshipService.class);

    private FriendshipRepository friendshipRepository;

    private UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public void removeFriend(long userId, long friendId) {
        FriendshipDBO friendship = friendshipRepository.findFriendship(userId, friendId);
        FriendshipDBO friendship2 = friendshipRepository.findFriendship2(userId, friendId);

        if (friendship != null) {
            friendshipRepository.delete(friendship);
        }
        if (friendship2 != null) {
            friendshipRepository.delete(friendship2);
        }
    }

    public List<UserDBO> getUsersNotFriendsOfUser(long userId) {
        UserDBO user = userRepository.findById(userId).get();

        List<UserDBO> allUsers = userRepository.findAll();
        List<UserDBO> friendUsers = friendshipRepository.findAllMyFriends(userId);
        List<UserDBO> friendUsers2 = friendshipRepository.findAllMyFriends2(userId);
        List<UserDBO> usersWhoReqMe = friendshipRepository.findWhoRequestedMe(userId);
        List<UserDBO> usersWhoIReq = friendshipRepository.findWhoIRequested(userId);

        List<UserDBO> nonFriendUsers = new ArrayList<>(allUsers);

        nonFriendUsers.removeAll(friendUsers);
        nonFriendUsers.removeAll(friendUsers2);
        nonFriendUsers.removeAll(usersWhoReqMe);
        nonFriendUsers.removeAll(usersWhoIReq);
        nonFriendUsers.remove(user);

        return nonFriendUsers;
    }

    public List<UserDBO> getRequestsOfUser(long userId) {
        return friendshipRepository.findWhoRequestedMe(userId);
    }

    public void sendFriendRequest(long userId, long friendId) {
        UserDBO user = userRepository.findById(userId).get();
        UserDBO friend = userRepository.findById(friendId).get();

        FriendshipDBO friendship = new FriendshipDBO();
        friendship.setUser(user);
        friendship.setFriend(friend);
        friendship.setStatus(Constants.FRIEND_STATUS_REQUESTED);

        List<UserDBO> allUsers = userRepository.findAll();
        List<UserDBO> nonFriendUsers = new ArrayList<>(allUsers);
        nonFriendUsers.remove(friend);

//        FriendshipDBO oldFriendship = friendshipRepository.findOldRecords(userId);
//        Long value = oldFriendship.getFriendshipId();
//        if(value != null){
//            friendshipRepository.delete(oldFriendship);
//        }


        friendshipRepository.save(friendship);
    }

    public void acceptFriendRequest(long userId, long friendId) {
        FriendshipDBO friendship = friendshipRepository.findFriendship(userId, friendId);
        FriendshipDBO oldRecord = null;
        if (friendship == null && friendship.getStatus() != Constants.FRIEND_STATUS_REQUESTED) {
            oldRecord = friendshipRepository.findOldRecords(userId);
        }
        friendship.setStatus(Constants.FRIEND_STATUS_ACCEPTED);

        if (oldRecord != null) {
            friendshipRepository.delete(oldRecord);
        }

        friendshipRepository.save(friendship);
    }

    public List<UserDBO> getFriendsOfUser(long userId) {
        List<UserDBO> myFriends1 = friendshipRepository.findAllMyFriends(userId);
        List<UserDBO> myFriends2 = friendshipRepository.findAllMyFriends2(userId);
        List<UserDBO> combinedFriends = new ArrayList<>(myFriends1);
        combinedFriends.addAll(myFriends2);

        return combinedFriends;
    }

}
