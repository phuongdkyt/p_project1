package com.example.demo.models.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class UserLogin implements Serializable {
    private Long id;
    private List<String> roles;
    private String username;
    private String accessToken;
}
