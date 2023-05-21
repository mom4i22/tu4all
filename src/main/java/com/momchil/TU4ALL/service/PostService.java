package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.FriendDBO;
import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.FriendRepository;
import com.momchil.TU4ALL.repository.PostRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(PostService.class);

    private PostRepository postRepository;

    private UserRepository userRepository;

    private FriendRepository friendRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendRepository friendRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
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
        UserDBO userDBO = postDBO.getCreator();
        userDBO.setLikeNotifications(userDBO.getLikeNotifications() + 1);
        userRepository.save(userDBO);
        postRepository.save(postDBO);
    }

    public void unlikePost(long id) {
        PostDBO postDBO = postRepository.findById(id).get();
        postDBO.setLikeCount(postDBO.getLikeCount() - 1);
        postRepository.save(postDBO);
    }

    public List<PostDBO> readAllByCreatorAndDate(long userId) {
        UserDBO userDBO = userRepository.findById(userId).get();
        List<FriendDBO> friendDBOS = userDBO.getFriends();
        List<PostDBO> postDBOS = null;
        long currentTime = System.currentTimeMillis();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date =  new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        long timeOneWeekAgo = date.getTime();
        for (FriendDBO friendDBO : friendDBOS) {
            UserDBO user = userRepository.findByAlias(friendDBO.getAlias());
            List<PostDBO> friendPosts = postRepository.findAllByCreator(user);
          // List<PostDBO> friendPosts = postRepository.findAllByCreatorAndDate(user,new Timestamp(timeOneWeekAgo),new Timestamp(currentTime));
            postDBOS.addAll(friendPosts);
        }
        return postDBOS;
    }
}
