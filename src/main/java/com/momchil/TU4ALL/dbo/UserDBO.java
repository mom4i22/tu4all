package com.momchil.TU4ALL.dbo;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private long userId;

    @Column(name = "ALIAS", unique = true)
    private String alias;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "FACULTY")
    private String faculty;

    @Column(name = "FACULTY_NUMBER", unique = true)
    private String facultyNumber;

    @Column(name = "PROFILE_PICTURE")
    @Lob
    private String profilePicture;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "LIKE_NOTIFICATIONS")
    private long likeNotifications;

    @Column(name = "COMMENT_NOTIFICATIONS")
    private long commentNotifications;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FriendDBO> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommentDBO> comments;

}
