package com.momchil.TU4ALL.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name="POST_ID",nullable = false)
    private PostDBO post;

}
