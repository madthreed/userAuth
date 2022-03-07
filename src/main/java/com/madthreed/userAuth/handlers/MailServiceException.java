package com.madthreed.userAuth.handlers;

public class MailServiceException extends Exception {

    public MailServiceException() {
        super();
    }

    public MailServiceException(String message) {
        super(message);
    }

    public MailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailServiceException(Throwable cause) {
        super(cause);
    }

    protected MailServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
