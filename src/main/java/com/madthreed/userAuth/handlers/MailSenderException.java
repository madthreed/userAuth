package com.madthreed.userAuth.handlers;

public class MailSenderException extends Exception {
    public MailSenderException() {
        super();
    }

    public MailSenderException(String message) {
        super(message);
    }

    public MailSenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSenderException(Throwable cause) {
        super(cause);
    }

    protected MailSenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
