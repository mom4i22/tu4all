package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.service.PostService;
import com.momchil.TU4ALL.service.UserService;
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

    private PostService postService;
    private UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-post", headers = "content-type=multipart/*")
    public ResponseEntity<?> createPost(@RequestParam Map<String, String> requestParams, @RequestParam("content") MultipartFile content) {
        String text = requestParams.get("text");
        String userId = requestParams.get("userId");
        postService.createPost(userId,text,content);
        return ResponseEntity.ok("Post created successfully");
    }

    @PutMapping("/edit-post/{id}")
    public ResponseEntity<?> editPost(@PathVariable long id, @RequestParam("text") String text) {
        postService.editPost(id, text);
        return ResponseEntity.ok("Post edited successfully");
    }

    @GetMapping("/get-user-posts/{id}")
    public ResponseEntity<?> getUserPosts(@PathVariable long id) {
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

    @PutMapping("/like-post/{id}")
    public ResponseEntity<?> likePost(@PathVariable long id) {
        postService.likePost(id);
        return ResponseEntity.ok("Liked post");
    }

    @PutMapping("/unlike-post/{id}")
    public ResponseEntity<?> unlikePost(@PathVariable long id) {
        postService.unlikePost(id);
        return ResponseEntity.ok("Unliked post");
    }

    @GetMapping("/get-timeline")
    public ResponseEntity<List<PostDBO>> getTimeline(@RequestParam("userId") String userId) {
        List<PostDBO> posts = postService.readAllByCreatorAndDate(Long.parseLong(userId));
        return ResponseEntity.ok(posts);
    }

}
