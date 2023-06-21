package com.momchil.TU4ALL.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "FRIENDSHIPS")
@Getter
@Setter
@RequiredArgsConstructor
public class FriendshipDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIENDSHIP_ID", nullable = false)
    private long friendshipId;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "FRIEND_ID")
    private long friendId;

    @Column(name = "STATUS")
    private int status;

}
