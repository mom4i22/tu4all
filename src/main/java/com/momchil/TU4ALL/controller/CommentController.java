package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.service.CommentService;
import com.momchil.TU4ALL.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/friends")
public class CommentController {

    private CommentService commentService;

    private PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/create-comment")
    public ResponseEntity<?> createComment(@RequestParam Map<String,String> requestParams) {
        String content = requestParams.get("content");
        String text = requestParams.get("text");
        String postId = requestParams.get("postId");
        long timeMillis = System.currentTimeMillis();
        PostDBO postDBO = postService.readById(Long.parseLong(postId));
        CommentDBO commentDBO = new CommentDBO();
        commentDBO.setContent(content);
        commentDBO.setText(text);
        commentDBO.setCreationDate(new Timestamp(timeMillis));
        commentDBO.setPost(postDBO);
        commentService.createComment(commentDBO);
        return ResponseEntity.ok(commentDBO);
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
