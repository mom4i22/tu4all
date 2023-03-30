package com.momchil.TU4ALL.model;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;

}
