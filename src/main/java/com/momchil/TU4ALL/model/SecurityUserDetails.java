package com.momchil.TU4ALL.model;

import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

public class SecurityUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public SecurityUserDetails(UserDBO user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
    }

    public SecurityUserDetails() {

    }

    public Long getId() {
        return id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "SecurityUserDetails{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
