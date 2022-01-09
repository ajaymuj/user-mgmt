package com.mycode.bms.usermgmt.exception;

import org.springframework.http.HttpStatus;

public class InvalidUserTypeException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public static final String USERTYPE_INVALID_ERROR = "Invalid Usertype! please provide correct usertype.";

    public InvalidUserTypeException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    private static String getErrorMsg(Exception exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof InvalidUserTypeException) {
            return USERTYPE_INVALID_ERROR;
        }
        return exception.getMessage();
    }
}
