package com.darkdemon.backend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashEncoder {

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public String encode(String raw){
        return bcrypt.encode(raw);
    }

    public Boolean matchP(String raw, String hashedInput){
        return bcrypt.matches(raw, hashedInput);
    }
}
