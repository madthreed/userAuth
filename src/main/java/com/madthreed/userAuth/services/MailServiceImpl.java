package com.madthreed.userAuth.services;

import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.handlers.MailSenderException;
import com.madthreed.userAuth.handlers.MailServiceException;
import com.madthreed.userAuth.utils.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final MailSender mailSender;

    @Override
    public void sendActivationMail(User user, String newPwd) throws MailServiceException {
        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "For activate your account please visit link: " +
                            "http://localhost:8080/user/activate/%s \n" +
                            "Login: %s\nPassword: %s \n",
                    user.getFirstname(), user.getVerificationToken().getToken(),
                    user.getUsername(), newPwd);

            try {
                mailSender.sendTextMail(user.getEmail(), "Activation code", message);
            } catch (MailSenderException e) {
                e.printStackTrace();
                throw new MailServiceException("send activation mail error", e.getCause());
            }
        }
    }
}
