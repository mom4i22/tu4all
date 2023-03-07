package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.repository.PostRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(PostService.class);

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDBO> readAll() {
        return postRepository.findAll();
    }

    public boolean deleteByPostId(long id) {
        try {
            PostDBO postDBO = postRepository.findById(id).get();
            postRepository.delete(postDBO);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public void createPost(PostDBO postDBO) {
        try {
            postRepository.save(postDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public PostDBO editPost(long id, PostDBO postDBO) {
        PostDBO post = postRepository.findById(id).get();
        post.setText(postDBO.getText());
        post.setContent(postDBO.getContent());
        post.setCreationDate(postDBO.getCreationDate());
        post.setComments(postDBO.getComments());
        post.setCreator(post.getCreator());
        postRepository.save(post);
        return post;
    }

}
