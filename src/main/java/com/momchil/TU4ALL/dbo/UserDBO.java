package com.momchil.TU4ALL.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
public class UserDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private long id;

    @Column(name = "ALIAS", unique = true)
    private String alias;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "DATE_OF_BIRTH")
    private Timestamp dateOfBirth;

    @Column(name = "FACULTY")
    private String faculty;

    @Column(name = "FACULTY_NUMBER", unique = true)
    private String facultyNumber;

    @Column(name="FRIENDS")
    @ElementCollection
    private List<String> friends;

}
