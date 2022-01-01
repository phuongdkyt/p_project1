package com.example.demo.models.in;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIn {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
}
