package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.repository.CommentRepository;
import com.momchil.TU4ALL.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentDBO> readAll(){
        return commentRepository.findAll();
    }

    public boolean deleteCommentById(long id) {
        try {
            CommentDBO commentDBO = commentRepository.findById(id).get();
            commentRepository.delete(commentDBO);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}