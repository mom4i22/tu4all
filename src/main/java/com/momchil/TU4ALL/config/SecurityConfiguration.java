package com.momchil.TU4ALL.config;

import com.momchil.TU4ALL.filters.JwtRequestFilter;
import com.momchil.TU4ALL.service.UserSecurityDetailsService;
import com.momchil.TU4ALL.utils.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserSecurityDetailsService userDetailsService;

    private JwtRequestFilter jwtRequestFilter;

    public SecurityConfiguration(UserSecurityDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/users/edit-user/**")
                .hasAnyRole("USER")
                .antMatchers("/users/delete-user/**")
                .hasAnyRole("USER")
                .antMatchers("/posts/create-post/**")
                .hasAnyRole("USER")
                .antMatchers("/users/get-people/**")
                .hasAnyRole("USER")
                .antMatchers("/posts/edit-post")
                .hasAnyRole("USER")
                .antMatchers("/posts/delete-post/**")
                .hasAnyRole("USER")
                .antMatchers("/posts/get-user-posts/**")
                .hasAnyRole("USER")
                .antMatchers("/posts/like-post")
                .hasAnyRole("USER")
                .antMatchers("/posts/unlike-post")
                .hasAnyRole("USER")
                .antMatchers("/posts/get-timeline/**")
                .hasAnyRole("USER")
                .antMatchers("/comments/create-comment/**")
                .hasAnyRole("USER")
                .antMatchers("/comments/get-comments")
                .hasAnyRole("USER")
                .antMatchers("/comments/edit-comment/**")
                .hasAnyRole("USER")
                .antMatchers("/comments/delete-comment/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/get-friends/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/send-request/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/accept-request/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/remove-friend/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/decline-request/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/block-user/**")
                .hasAnyRole("USER")
                .antMatchers("/friends/get-non-friends/**")
                .hasAnyRole("USER")
                .antMatchers("/users/get-all-courses-for-user/**")
                .hasAnyRole("USER")
                .antMatchers("/courses/create-course/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/get-course/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/get-courses/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/delete-course/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/add-student-to-course/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/remove-student-from-course/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/get-students-for-course/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/courses/get-all-courses-for-user-teacher/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/users/get-all-users/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/users/get-user-by-alias/**")
                .permitAll()
                .antMatchers("users/create-user")
                .permitAll()
                .antMatchers("/auth/login")
                .permitAll().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
