package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.repository.CommentRepository;
import com.momchil.TU4ALL.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentDBO> readAll(){
        return commentRepository.findAll();
    }

    @Modifying
    @Transactional
    public void deleteCommentById(long commentId) {
        commentRepository.removeByCommentId(commentId);
    }
}
