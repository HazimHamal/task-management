package com.example.hzv1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @JsonProperty("Username")
    private String username;

    @NotBlank(message = "Password is required")
    @JsonProperty("Password")
    private String password;
}
