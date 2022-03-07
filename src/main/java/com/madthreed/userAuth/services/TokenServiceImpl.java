package com.madthreed.userAuth.services;

import com.madthreed.userAuth.domain.Token;
import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.repos.TokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private static final int EXPIRATION = 60 * 24;

    private final TokenRepo tokenRepo;
    private final MailService mailService;
    private final PasswordService passwordService;

    @Override
    public String generateTokenAndPwdForUser(User user) {
        /* Password stores in String, because we send it to user in E-mail
         * There's no need to use char[] or some else
         */

        String randomToken = UUID.randomUUID().toString();
        String newPassword = passwordService.generateRandomPassword();
        Date expiryDate = calculateExpiryDate();
        Token token;

        if (user.getVerificationToken() == null) {
            token = new Token(randomToken, passwordService.encode(newPassword), user, expiryDate);
        } else {
            token = user.getVerificationToken();
            token.setToken(randomToken);
            token.setNewPassword(passwordService.encode(newPassword));
            token.setExpiryDate(expiryDate);
        }

        user.setVerificationToken(token);
        tokenRepo.save(token);

        return newPassword;
    }

    @Override
    public User getUserByToken(String token) {
        Token byToken = tokenRepo.findByToken(token);
        return byToken != null ? byToken.getUser() : null;
    }

    @Override
    public void deactivateTokenByUser(User user) {
        Token token = user.getVerificationToken();
        if (token != null) {
            tokenRepo.delete(token);
        }
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
}
