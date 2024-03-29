package com.momchil.TU4ALL.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwt;

    private String email;

    private String password;

    private Long userId;

    private String alias;

    private String name;

    private String profilePic;

    private int likeNotifications;

    private int commentNotifications;

    private String role;

}
