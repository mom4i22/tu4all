package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.PostRepository;
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
    private UserService userService;
   // private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostDBO readById(long id) {
        return postRepository.findById(id).get();
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

    public void createPost(String userId, String text, MultipartFile content) {
        long timeMillis = System.currentTimeMillis();
        String fileName = StringUtils.cleanPath(content.getOriginalFilename());
        if(fileName.contains("..")) {
            logger.error("Not a valid file name");
        }
        UserDBO userDBO = userService.readById(Long.parseLong(userId));
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
