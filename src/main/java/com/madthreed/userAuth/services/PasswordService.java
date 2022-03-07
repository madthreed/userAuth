package com.madthreed.userAuth.services;

public interface PasswordService {
    String generateRandomPassword();

    String encode(String password);
}
