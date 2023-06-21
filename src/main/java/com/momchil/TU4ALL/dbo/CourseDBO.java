package com.momchil.TU4ALL.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class CourseDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID", nullable = false)
    private long courseId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SUBJECT")
    private String subject;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "TEACHER", nullable = false)
    private UserDBO teacher;

    @ManyToMany
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserDBO> students;

}