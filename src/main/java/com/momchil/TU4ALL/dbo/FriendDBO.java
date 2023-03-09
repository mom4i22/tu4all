package com.momchil.TU4ALL.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "FRIENDS")
public class FriendDBO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private long id;

    private String alias;

    private long status;

}
