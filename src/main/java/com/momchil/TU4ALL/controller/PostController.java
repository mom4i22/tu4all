package com.momchil.TU4ALL.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @DeleteMapping
    public void deletePost(long id) {}

}
