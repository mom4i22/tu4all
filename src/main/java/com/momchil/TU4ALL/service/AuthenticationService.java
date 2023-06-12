package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.model.AuthenticationResponse;
import com.momchil.TU4ALL.utils.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(PostService.class);
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse authenticate(String username, String password)  {
        logger.info("Authenticating");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserDBO userDBO = userService.readByEmail(userDetails.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            logger.info("Authentication is successful");
            logger.info("Authentication is successful for user with data: {}", userDetails);
            return new AuthenticationResponse(jwt, userDBO.getEmail(), userDBO.getPassword(), userDBO.getUserId());
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed: " + e.getMessage());
            throw e;
        }
    }

    public String refreshToken(String oldJwt) {
        logger.info("Refreshing token");
        oldJwt = oldJwt.substring(7);

        if (jwtUtil.validateTokenToRefresh(oldJwt)) {
            String username = jwtUtil.extractUsernameFromPayload(oldJwt.split("\\.")[1]);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            logger.info("Json Web Token is refreshed");

            return jwtUtil.generateToken(userDetails);
        } else {
            throw new RuntimeException();
        }
    }
}
