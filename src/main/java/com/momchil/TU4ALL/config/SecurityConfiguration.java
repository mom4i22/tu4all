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
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/users/edit-user/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/users/delete-user/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/users/change-password/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/create-post")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/edit-post/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/delete-post/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/get-user-posts/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/like-post/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/unlike-post/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/posts/get-timeline")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/comments/create-comment")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/comments/edit-comment/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/comments/delete-comment/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/friends/get-friends/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/friends/add-friend/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/friends/accept-friend/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/friends/remove-friend/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
                .antMatchers("/friends/block-user/**")
                .hasAnyRole(Roles.ROLE_ADMIN.getValue())
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
