package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.CommentDBO;
import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.model.CreateCommentRequest;
import com.momchil.TU4ALL.service.CommentService;
import com.momchil.TU4ALL.service.PostService;
import com.momchil.TU4ALL.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

    @PostMapping("/create-comment/{id}")
    public ResponseEntity<?> createComment(@PathVariable long id, @RequestBody CreateCommentRequest createCommentRequest) {
        long timeMillis = System.currentTimeMillis();
        PostDBO postDBO = postService.readById(createCommentRequest.getPostId());
        UserDBO userDBO = userService.readById(id);
        CommentDBO commentDBO = new CommentDBO();
        commentDBO.setText(createCommentRequest.getText());
        commentDBO.setCreationDate(new Timestamp(timeMillis));
        commentDBO.setPost(postDBO);
        commentDBO.setUser(userDBO);
        commentService.createComment(commentDBO);
        UserDBO creator = postDBO.getCreator();
        if(creator.getCommentNotifications() == 0) {
            creator.setCommentNotifications(1);
        }
        creator.setCommentNotifications(creator.getCommentNotifications() + 1);
        return ResponseEntity.ok(commentDBO);
    }

    @GetMapping("/get-comments")
    public ResponseEntity<List<CommentDBO>> getComments(@RequestParam("postId")  String postId) {
        List<CommentDBO> comments = null;
        comments = commentService.readyByPost(Long.parseLong(postId));
        return ResponseEntity.ok(comments);
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
