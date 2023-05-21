package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.repository.CommentRepository;
import com.momchil.TU4ALL.repository.PostRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public void createComment(CommentDBO commentDBO) {
        try {
            commentRepository.save(commentDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
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

    public void editComment(long id, String text) {
        long timeMillis = System.currentTimeMillis();
        CommentDBO commentDBO = commentRepository.findById(id).get();
        commentDBO.setText(text);
        commentDBO.setEditDate(new Timestamp(timeMillis));
        commentRepository.save(commentDBO);
    }

}
