package com.momchil.TU4ALL.dbo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name="edit_date")
    private Timestamp editDate;

    @Column(name = "like_count")
    private long likeCount;

    @ManyToOne
    @JoinColumn(name="CREATOR", nullable = false)
    private UserDBO creator;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommentDBO> comments;

}
