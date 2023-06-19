package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.CommentRepository;
import com.momchil.TU4ALL.repository.FriendshipRepository;
import com.momchil.TU4ALL.repository.PostRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(PostService.class);

    private PostRepository postRepository;
    private UserRepository userRepository;
    private FriendshipRepository friendshipRepository;
    private CommentRepository commentRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository, FriendshipRepository friendshipRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.commentRepository = commentRepository;
    }

    public PostDBO readById(long id) {
        return postRepository.findById(id).get();
    }

    public List<PostDBO> readAll() {
        return postRepository.findAll();
    }

    public void createPost(Long userId, String description, MultipartFile content) {
        long timeMillis = System.currentTimeMillis();
        String fileName = StringUtils.cleanPath(content.getOriginalFilename());
        if (fileName.contains("..")) {
            logger.error("Not a valid file name");
        }
        UserDBO userDBO = userRepository.findById(userId).get();
        PostDBO postDBO = new PostDBO();
        postDBO.setText(description);
        try {
            postDBO.setContent(Base64.getEncoder().encodeToString(content.getBytes()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        postDBO.setCreationDate(new Timestamp(timeMillis));
        postDBO.setCreator(userDBO);
        postRepository.save(postDBO);
    }

    public void createPostWithoutContent(long userId, String description){
        long timeMillis = System.currentTimeMillis();

        UserDBO userDBO = userRepository.findById(userId).get();
        PostDBO postDBO = new PostDBO();
        postDBO.setText(description);

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
            List<CommentDBO> commentDBOS = commentRepository.findAllByPost(postDBO);
            for(CommentDBO commentDBO : commentDBOS) {
                commentRepository.delete(commentDBO);
            }
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
        if(userDBO.getLikeNotifications() == 0) {
            userDBO.setLikeNotifications(1);
        }
        else userDBO.setLikeNotifications(userDBO.getLikeNotifications() + 1);
        userRepository.save(userDBO);
        postRepository.save(postDBO);
    }

    public void unlikePost(long id) {
        PostDBO postDBO = postRepository.findById(id).get();
        postDBO.setLikeCount(postDBO.getLikeCount() - 1);
        UserDBO userDBO = postDBO.getCreator();

        if(userDBO.getLikeNotifications() == 0) {
            userDBO.setLikeNotifications(1);
        }
        else userDBO.setLikeNotifications(userDBO.getLikeNotifications() - 1);
        postRepository.save(postDBO);
    }

    private long getTimeStamp() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        return date.getTime();
    }

    public List<PostDBO> readAllByCreatorAndDate(long userId) {

        List<PostDBO> myFeed = new ArrayList<>();

        List<UserDBO> myFriends1 = friendshipRepository.findAllMyFriends(userId);
        List<UserDBO> myFriends2 = friendshipRepository.findAllMyFriends2(userId);

        List<UserDBO> combinedFriends = new ArrayList<>(myFriends1);
        combinedFriends.addAll(myFriends2);

        if (combinedFriends.size() > 0) {
            for (UserDBO user : combinedFriends) {
                List<PostDBO> friendPosts = postRepository.findAllByCreatorAndDate(user, new Timestamp(getTimeStamp()), new Timestamp(System.currentTimeMillis()));

                if (friendPosts.size() >0) {
                    myFeed.addAll(friendPosts);
                }
            }

        }

        return myFeed;
    }
}
