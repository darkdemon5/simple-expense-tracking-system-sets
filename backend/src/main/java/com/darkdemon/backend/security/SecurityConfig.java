package com.darkdemon.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated());
        return http.build();
//        http
//                .csrf(csrf -> csrf.disable()) // disable CSRF for testing (Postman)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/signup").permitAll() // allow signup without auth
//                        .anyRequest().authenticated() // everything else requires login
//                )
//                .httpBasic(httpBasic -> {});    // keep basic auth enabled

//        Claude:
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for REST API
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/api/auth/**").permitAll() // Allow
//                        signup/login
//                                .anyRequest().authenticated() // Protect other endpoints
//                )
//                .sessionManagement(session -> session
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
//                        No sessions, we'll use JWT
//              );

//        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
