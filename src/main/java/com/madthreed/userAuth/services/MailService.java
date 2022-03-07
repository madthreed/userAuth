package com.madthreed.userAuth.services;

import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.handlers.MailServiceException;

public interface MailService {
    void sendActivationMail(User user, String newPwd) throws MailServiceException;
}
