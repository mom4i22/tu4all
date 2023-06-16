package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.model.LikeRequest;
import com.momchil.TU4ALL.model.PostRequest;
import com.momchil.TU4ALL.service.PostService;
import com.momchil.TU4ALL.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/posts")
public class PostController {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(PostController.class);

    private PostService postService;
    private UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-post/{id}", headers = "content-type=multipart/*")
    public ResponseEntity<?> createPost(@PathVariable long id, @RequestParam("description") String description, @RequestParam("content") MultipartFile content) {
        UserDBO userDBO = userService.readById(id);
        postService.createPost(userDBO.getUserId(), description, content);
        return ResponseEntity.ok("Post created successfully");
    }

    @PutMapping(value = "/edit-post", headers = "content-type=application/json")
    public ResponseEntity<?> editPost(@RequestBody PostRequest postRequest) {
        postService.editPost(postRequest.getId(), postRequest.getText());
        return ResponseEntity.ok("Post edited successfully");
    }

    @GetMapping(value = "/get-user-posts/{id}")
    public ResponseEntity<?> getUserPosts(@PathVariable long id) {
        logger.info(String.valueOf(id));
        List<PostDBO> posts = postService.getPostsForUser(id);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable  long id) {
        boolean deleted = false;
        deleted = postService.deleteByPostId(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/like-post")
    public ResponseEntity<?> likePost(@RequestBody LikeRequest likeRequest) {
        postService.likePost(likeRequest.getPostId());
        return ResponseEntity.ok("Liked post");
    }

    @PutMapping("/unlike-post")
    public ResponseEntity<?> unlikePost(@RequestBody LikeRequest likeRequest) {
        postService.unlikePost(likeRequest.getPostId());
        return ResponseEntity.ok("Unliked post");
    }

    @GetMapping("/get-timeline/{userId}")
    public ResponseEntity<List<PostDBO>> getTimeline(@PathVariable long userId) {
        List<PostDBO> posts = postService.readAllByCreatorAndDate(userId);
        return ResponseEntity.ok(posts);
    }

}
