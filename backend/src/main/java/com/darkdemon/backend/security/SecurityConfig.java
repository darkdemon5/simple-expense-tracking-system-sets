package com.darkdemon.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // disable CSRF for testing (Postman)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/signup").permitAll() // allow signup without auth
//                        .anyRequest().authenticated() // everything else requires login
//                )
//                .httpBasic(httpBasic -> {});    // keep basic auth enabled

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
