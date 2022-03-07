package com.madthreed.userAuth.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordEncoder passwordEncoder;

    public PasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generateRandomPassword() {
        return "123";
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
