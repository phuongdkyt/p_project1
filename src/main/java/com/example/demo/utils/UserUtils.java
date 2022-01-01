package com.example.demo.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtils {
    public static String toRole(List<GrantedAuthority> roles) {
        return roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }
    public static List<String> toRoleResponse(String roles) {
        return Arrays.asList(roles.split(","));
    }
}
