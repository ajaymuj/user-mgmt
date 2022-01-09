package com.mycode.bms.usermgmt.exception;

import org.springframework.http.HttpStatus;

public class InvalidUserTokenException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public static final String INVALID_USERTOKEN_ERROR = "userToken is invalid!";

    public InvalidUserTokenException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public InvalidUserTokenException(Exception exception) {
        this(HttpStatus.BAD_REQUEST, getErrorMsg(exception));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    private static String getErrorMsg(Exception exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof InvalidUserTokenException) {
            return INVALID_USERTOKEN_ERROR;
        }
        return exception.getMessage();
    }
}
