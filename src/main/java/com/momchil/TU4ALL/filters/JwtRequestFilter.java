package com.momchil.TU4ALL.filters;

import com.momchil.TU4ALL.service.UserSecurityDetailsService;
import com.momchil.TU4ALL.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private UserSecurityDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserSecurityDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String jwt;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            jwt = authorizationHeader.substring(7);
//            if (!request.getRequestURL().toString().equals("http://localhost:8080/users")) {
                if (jwtUtil.validateToken(jwt)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.info("Token is valid");
                }
            //}
        }
        filterChain.doFilter(request, response);
    }
}