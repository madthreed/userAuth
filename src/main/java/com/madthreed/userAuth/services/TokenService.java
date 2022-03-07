package com.madthreed.userAuth.services;

import com.madthreed.userAuth.domain.User;

public interface TokenService {
    String generateTokenAndPwdForUser(User user);

    User getUserByToken(String token);

    void deactivateTokenByUser(User user);
}
