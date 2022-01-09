package com.mycode.bms.usermgmt.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;

public class DuplicateUsernameException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public static final String USERNAME_UNIQUENESS_ERROR = "username already exists, please provide different username";

    public DuplicateUsernameException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public DuplicateUsernameException(Exception exception) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, getErrorMsg(exception));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    private static String getErrorMsg(Exception exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) cause;
            String message = cve.getConstraintName();
                return USERNAME_UNIQUENESS_ERROR;
        }
        return exception.getMessage();
    }
}
