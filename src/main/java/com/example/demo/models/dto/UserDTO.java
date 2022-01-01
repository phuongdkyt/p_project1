package com.example.demo.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private String username;
    private String email;
    private String address;
}
