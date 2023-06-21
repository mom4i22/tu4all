package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.FriendshipDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipDBO, Long> {
    //retrieve all requests -by getting those who requested me first, and by getting those whom I requested first
    @Query("SELECT f.userId FROM FriendshipDBO f WHERE f.friendId = :userId AND f.status = 1")
    List<Long> findWhoRequestedMe(long userId);

    @Query("SELECT f.friendId FROM FriendshipDBO f WHERE f.userId = :userId AND f.status = 1")
    List<Long> findWhoIRequested(long userId);

    //retrieve friends
    @Query("SELECT f.userId FROM FriendshipDBO f WHERE f.friendId = :userId AND f.status = 2")
    List<Long> findAllMyFriends(long userId);
    @Query("SELECT f.friendId FROM FriendshipDBO f WHERE f.userId = :userId AND f.status = 2")
    List<Long> findAllMyFriends2(long userId);

    //get old records
    @Query("SELECT f FROM FriendshipDBO f WHERE f.friendId = :userId OR f.userId = :userId AND f.status = 0")
    FriendshipDBO findOldRecords(long userId);

    @Query("SELECT f FROM FriendshipDBO f WHERE f.userId = :userId AND f.friendId = :friendId AND (f.status = 1 OR f.status = 2)")
    FriendshipDBO findFriendship(long userId, long friendId);
    @Query("SELECT f FROM FriendshipDBO f WHERE f.userId = :friendId AND f.friendId = :userId AND (f.status = 1 OR f.status = 2)")
    FriendshipDBO findFriendship2(long userId, long friendId);

}
