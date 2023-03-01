package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.repository.PostRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
}
