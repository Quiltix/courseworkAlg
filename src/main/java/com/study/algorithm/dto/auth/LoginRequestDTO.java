package com.study.algorithm.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @Size(min = 3, message = "username must be at least 3 characters long")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Size(min = 5, message = "Password must be at least 5 characters long")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}