package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.LoginDTO;
import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.model.User;
import com.darkdemon.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user")
    private List<User> getUser() {
        return authService.getUser();
    }

    @PostMapping("/signup")
    private ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userdto) {
        return authService.signUp(userdto);
    }

    @PostMapping("/signin")
    private ResponseEntity<?> signIn(LoginDTO loginDTO) {
        return authService.signIn(loginDTO);
    }

    @PostMapping("/refresh")
    private ResponseEntity<?> refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
