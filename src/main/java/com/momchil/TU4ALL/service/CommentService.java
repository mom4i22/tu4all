package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.repository.CommentRepository;
import com.momchil.TU4ALL.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

}
