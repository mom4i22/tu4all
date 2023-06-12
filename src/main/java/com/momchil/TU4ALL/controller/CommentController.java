package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.service.CommentService;
import com.momchil.TU4ALL.service.PostService;
import com.momchil.TU4ALL.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    private PostService postService;

    private UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create-comment")
    public ResponseEntity<?> createComment(@RequestParam("text") String text, @RequestParam("postId") String postId, @RequestParam("email") String email) {
        long timeMillis = System.currentTimeMillis();
        PostDBO postDBO = postService.readById(Long.parseLong(postId));
        UserDBO userDBO = userService.readByEmail(email);
        CommentDBO commentDBO = new CommentDBO();
        commentDBO.setText(text);
        commentDBO.setCreationDate(new Timestamp(timeMillis));
        commentDBO.setPost(postDBO);
        commentDBO.setUser(userDBO);
        commentService.createComment(commentDBO);
        UserDBO creator = postDBO.getCreator();
        creator.setCommentNotifications(creator.getCommentNotifications() + 1);
        return ResponseEntity.ok(commentDBO);
    }

    @PutMapping("/edit-comment/{id}")
    public ResponseEntity<?> editComment(@PathVariable long id, @RequestParam("text") String text) {
        commentService.editComment(id, text);
        return ResponseEntity.ok("Comment edited successfully");
    }

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteComment(@PathVariable long id) {
        boolean deleted = false;
        deleted = commentService.deleteCommentById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

}
