package com.momchil.TU4ALL.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class PostDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID", nullable = false)
    private long postId;

    @Column(name = "TEXT")
    private String text;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name="CREATOR", nullable = false)
    private UserDBO creator;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentDBO> comments;

}
