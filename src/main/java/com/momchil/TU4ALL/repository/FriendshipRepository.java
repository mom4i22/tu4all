package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.FriendshipDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipDBO, Long> {
    @Query("SELECT f.friend FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.status = 2")
    List<UserDBO> findFriendsByUserId(long userId);

    //retrieve all requests -by getting those who requested me first, and by getting those whom I requested first
    @Query("SELECT f.user FROM FriendshipDBO f WHERE f.friend.userId = :userId AND f.status = 1")
    List<UserDBO> findWhoRequestedMe(long userId);

    @Query("SELECT f.friend FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.status = 1")
    List<UserDBO> findWhoIRequested(long userId);

    //retrieve friends
    @Query("SELECT f.user FROM FriendshipDBO f WHERE f.friend.userId = :userId AND f.status = 2")
    List<UserDBO> findAllMyFriends(long userId);
    @Query("SELECT f.friend FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.status = 2")
    List<UserDBO> findAllMyFriends2(long userId);

    //get old records
    @Query("SELECT f FROM FriendshipDBO f WHERE f.friend.userId = :userId OR f.user.userId = :userId AND f.status = 0")
    FriendshipDBO findOldRecords(long userId);

//    //retrieve other people
//    @Query("SELECT f.friend FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.status = 2")
//    List<UserDBO> findAllOtherPeople(long userId);

    @Query("SELECT f.friend FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.status = 2")
    List<UserDBO> findByAcceptedFriends(long userId);

    @Query("SELECT f.friend FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.status = 2")
    List<UserDBO> findFriendsByUserIdAndStatus(long userId);

    @Query("SELECT f FROM FriendshipDBO f WHERE f.user.userId = :userId AND f.friend.userId = :friendId AND (f.status = 1 OR f.status = 2)")
    FriendshipDBO findFriendship(long userId, long friendId);
    @Query("SELECT f FROM FriendshipDBO f WHERE f.user.userId = :friendId AND f.friend.userId = :userId AND (f.status = 1 OR f.status = 2)")
    FriendshipDBO findFriendship2(long userId, long friendId);

    FriendshipDBO findByUser_UserIdAndFriend_UserId(long userId, long friendId);


}
