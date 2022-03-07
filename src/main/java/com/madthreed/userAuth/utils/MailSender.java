package com.madthreed.userAuth.utils;

import com.madthreed.userAuth.handlers.MailSenderException;

public interface MailSender {

    void sendTextMail(String emailTo, String subject, String message) throws MailSenderException;
}
