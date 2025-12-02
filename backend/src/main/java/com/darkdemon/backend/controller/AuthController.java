package com.darkdemon.backend.controller;

import com.darkdemon.backend.dto.LoginDTO;
import com.darkdemon.backend.dto.UserDTO;
import com.darkdemon.backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/signup")
    private ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userdto, HttpServletResponse response) {
        return authService.signUp(userdto, response);
    }

    @PostMapping("/signin")
    private ResponseEntity<?> signIn(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        return authService.signIn(loginDTO, response);
    }

    @PostMapping("/logout")
    private ResponseEntity<?> logoutUser(HttpServletResponse response){
        return authService.logoutUser(response);
    }

    @PostMapping("/refresh")
    private ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if(cookies != null){
            for (Cookie cookie : cookies){
            if("refreshToken".equals(cookie.getName())){
                refreshToken = cookie.getValue();
                break;
            }
            }
        }

        if(refreshToken == null){
            return ResponseEntity.status(401).body("Refresh token not found");
        }
        return authService.refresh(refreshToken, response);
    }

}
