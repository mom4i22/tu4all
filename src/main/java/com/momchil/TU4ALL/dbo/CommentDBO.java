package com.momchil.TU4ALL.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID", nullable = false)
    private long commentId;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name="POST_ID",nullable = false)
    private PostDBO post;

}
