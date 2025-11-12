package com.darkdemon.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Credentials")
    private String email;

    @NotBlank(message = "password is required")
    @Email(message = "Invalid Credentials")
    private String password;
}
