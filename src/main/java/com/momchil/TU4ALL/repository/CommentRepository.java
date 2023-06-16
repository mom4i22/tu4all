package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.dbo.PostDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentDBO, Long> {

    @Query("SELECT comment FROM CommentDBO comment WHERE comment.post = ?1 ORDER BY post.creationDate desc")
    List<CommentDBO> findAllByPost(PostDBO postDBO);

}
