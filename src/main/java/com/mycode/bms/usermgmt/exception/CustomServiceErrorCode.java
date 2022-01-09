package com.mycode.bms.usermgmt.exception;

import org.springframework.http.HttpStatus;

public class CustomServiceErrorCode extends ServiceErrorCode {

    final public static ServiceErrorCode USER_NOT_FOUND
            = new CustomServiceErrorCode("USER_NOT_FOUND",
            HttpStatus.NOT_FOUND, "User not found");
    final public static ServiceErrorCode ROLE_NOT_FOUND
            = new CustomServiceErrorCode("ROLE_NOT_FOUND",
            HttpStatus.NOT_FOUND, "User role not found");
    final public static ServiceErrorCode INVALID_USER_TOKEN
            = new CustomServiceErrorCode("INVALID_USER_TOKEN",
            HttpStatus.NOT_FOUND, "Invalid user token");

    protected CustomServiceErrorCode(String errorCode, HttpStatus httpStatus, String defaultMessage) {
        super(errorCode, httpStatus, defaultMessage);
    }
}
