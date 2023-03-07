package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.service.PostService;
import com.momchil.TU4ALL.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PostController {

    private PostService postService;
    private UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create-post")
    public ResponseEntity<PostDBO> createPost(@RequestParam Map<String, String> requestParams) {
        String text = requestParams.get("text");
        String content = requestParams.get("content");
        long timeMillis = System.currentTimeMillis();
        String userId = requestParams.get("userId");
        UserDBO userDBO = userService.readById(Long.parseLong(userId));
        PostDBO postDBO = new PostDBO();
        postDBO.setText(text);
        postDBO.setContent(content);
        postDBO.setCreationDate(new Timestamp(timeMillis));
        postDBO.setCreator(userDBO);
        return ResponseEntity.ok(postDBO);
    }

    @PutMapping("/edit-post/{id}")
    public ResponseEntity<?> editPost(@PathVariable long id, @RequestBody PostDBO postDBO) {
        postDBO = postService.editPost(id, postDBO);
        return ResponseEntity.ok(postDBO);
    }

    @GetMapping("/")

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable  long id) {
        boolean deleted = false;
        deleted = postService.deleteByPostId(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

}
