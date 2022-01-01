package com.example.demo.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.models.entity.User;
import lombok.Builder;



@Builder
public class UserJwt {
    private Long id;
    private String username;
    private String role;

    public UserJwt(Long id, String accountId, String role) {
        this.id = id;
        this.username = accountId;
        this.role = role;
    }

    public UserJwt(Long id, String accountId) {
        this.id = id;
        this.username = accountId;
    }

    public static UserJwt from(DecodedJWT decodedJWT) {
        return new UserJwt(
                decodedJWT.getClaim("id").asLong(),
                decodedJWT.getClaim("username").asString(),
                decodedJWT.getClaim("role").asString()
        );
    }

    public static UserJwt from(User users, String role) {
        return new UserJwt(
                users.getId(),
                users.getUsername(),
                role
        );

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
