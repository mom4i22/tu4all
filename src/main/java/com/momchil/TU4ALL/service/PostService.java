package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.PostRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
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

    public PostDBO readById(long id) {
        return postRepository.findById(id).get();
    }

    public List<PostDBO> readAll() {
        return postRepository.findAll();
    }

    public void createPost(String userId, String text, MultipartFile content) {
        long timeMillis = System.currentTimeMillis();
        String fileName = StringUtils.cleanPath(content.getOriginalFilename());
        if(fileName.contains("..")) {
            logger.error("Not a valid file name");
        }
        UserDBO userDBO = userRepository.findById(Long.parseLong(userId)).get();
        PostDBO postDBO = new PostDBO();
        postDBO.setText(text);
        try {
            postDBO.setContent(Base64.getEncoder().encodeToString(content.getBytes()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        postDBO.setCreationDate(new Timestamp(timeMillis));
        postDBO.setCreator(userDBO);
        postRepository.save(postDBO);
    }

    public PostDBO editPost(long id, String text) {
        long timeMillis = System.currentTimeMillis();
        PostDBO post = postRepository.findById(id).get();
        post.setText(text);
        post.setEditDate(new Timestamp(timeMillis));
        postRepository.save(post);
        return post;
    }

    public List<PostDBO> getPostsForUser(long id) {
        UserDBO userDBO = userRepository.findById(id).get();
        return postRepository.findAllByCreator(userDBO);
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

    public void likePost(long id) {
        PostDBO postDBO = postRepository.findById(id).get();
        postDBO.setLikeCount(postDBO.getLikeCount() + 1);
        postRepository.save(postDBO);
    }

}
