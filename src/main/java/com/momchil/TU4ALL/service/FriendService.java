package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.FriendDBO;
import com.momchil.TU4ALL.repository.FriendRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(FriendService.class);

    private FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public void addFriend(FriendDBO friendDBO) {
        try {
            friendRepository.save(friendDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void removeFriend(long id) {
        FriendDBO friendDBO = friendRepository.findById(id).get();
        try {
            friendRepository.delete(friendDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
