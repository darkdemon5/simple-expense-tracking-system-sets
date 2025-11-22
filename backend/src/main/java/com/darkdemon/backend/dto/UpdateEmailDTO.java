package com.darkdemon.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Credentials")
    private String currentEmail;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Credentials")
    private String newEmail;
}
