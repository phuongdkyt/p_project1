package com.example.demo.services;

import com.example.demo.models.dto.Role;
import com.example.demo.models.entity.Permission;
import com.example.demo.models.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails , OAuth2User {

    private  Long id;

    private  String username;

    private String email;

    @JsonIgnore
    private String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;

    public UserDetailsImpl(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user, List<Permission> permissions) {
        List<GrantedAuthority> authorities = permissions.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username == null ? this.email : username;
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("id"));
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsImpl(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserDetailsImpl create(User user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(Role.CUSTOMER.name()));

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                authorities
        );
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static UserDetailsImpl create(User user, Map<String, Object> attributes) {
        UserDetailsImpl userOAuth = UserDetailsImpl.create(user);
        userOAuth.setAttributes(attributes);
        return userOAuth;
    }
}
