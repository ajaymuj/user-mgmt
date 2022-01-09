package com.mycode.bms.usermgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InvalidUserException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public static final String USERNAME_INVALID_ERROR = "username does not exists! please Register.";

    public InvalidUserException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public InvalidUserException(Exception exception) {
        this(HttpStatus.NOT_FOUND, getErrorMsg(exception));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private static String getErrorMsg(Exception exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof UsernameNotFoundException) {
            return USERNAME_INVALID_ERROR;
        }
        return exception.getMessage();
    }
}
