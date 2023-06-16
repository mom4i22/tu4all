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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                .antMatchers("/users/get-user-by-alias/**")
               .permitAll()
                .antMatchers("/users/edit-user/**")
               .permitAll()
                .antMatchers("/users/delete-user/**")
               .permitAll()
                .antMatchers("/posts/create-post/**")
                .permitAll()
                .antMatchers("/users/get-people/**")
                .permitAll()
                .antMatchers("/posts/edit-post")
               .permitAll()
                .antMatchers("/posts/delete-post/**")
               .permitAll()
                .antMatchers("/posts/get-user-posts/**")
               .permitAll()
                .antMatchers("/posts/like-post")
               .permitAll()
                .antMatchers("/posts/unlike-post")
               .permitAll()
                .antMatchers("/posts/get-timeline/**")
               .permitAll()
                .antMatchers("/comments/create-comment/**")
               .permitAll()
                .antMatchers("/comments/get-comments")
                .permitAll()
                .antMatchers("/comments/edit-comment/**")
               .permitAll()
                .antMatchers("/comments/delete-comment/**")
               .permitAll()
                .antMatchers("/friends/get-friends/**")
                .permitAll()
                .antMatchers("/friends/send-request/**")
                .permitAll()
                .antMatchers("/friends/accept-request/**")
                .permitAll()
                .antMatchers("/friends/remove-friend/**")
                .permitAll()
                .antMatchers("/friends/decline-request/**")
                .permitAll()
                .antMatchers("/friends/block-user/**")
                .permitAll()
                .antMatchers("/friends/get-non-friends/**")
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
