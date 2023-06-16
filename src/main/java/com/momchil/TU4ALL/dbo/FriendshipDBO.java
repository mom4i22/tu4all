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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserDBO user;

    @ManyToOne
    @JoinColumn(name = "FRIEND_ID")
    private UserDBO friend;

    @Column(name = "STATUS")
    private int status;

}
