package com.momchil.TU4ALL.model;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String text;

    private long postId;
}
