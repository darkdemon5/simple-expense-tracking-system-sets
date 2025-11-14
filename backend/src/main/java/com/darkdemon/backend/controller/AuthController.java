package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.LoginDTO;
import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user")
    private ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        return authService.getUser(token);
    }

    @PostMapping("/signup")
    private ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userdto) {
        return authService.signUp(userdto);
    }

    @PostMapping("/signin")
    private ResponseEntity<?> signIn(@RequestBody LoginDTO loginDTO) {
        return authService.signIn(loginDTO);
    }

    @PostMapping("/refresh")
    private ResponseEntity<?> refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @PutMapping("/update")
    private ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserDTO userDTO){
        return authService.updateUser(token, userDTO);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token){
        return authService.deleteUser(token);
    }
}
