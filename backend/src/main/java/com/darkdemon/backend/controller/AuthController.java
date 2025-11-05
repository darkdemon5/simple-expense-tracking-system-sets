package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
private String getUser(){
    return "Fuck You!!!";
}

@PostMapping("/signup")
    private ResponseEntity<String> signUp(@Valid @RequestBody UserDTO userdto){
        return authService.signUp(userdto);
    }
}
