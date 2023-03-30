package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.model.AuthenticationRequest;
import com.momchil.TU4ALL.service.AuthenticationService;
import com.momchil.TU4ALL.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    }

}
