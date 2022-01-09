package com.mycode.bms.usermgmt.exception;

import org.springframework.http.HttpStatus;

public class ServiceErrorCode {

    final public static ServiceErrorCode GLOBAL_INTERNAL_ERROR
            = new ServiceErrorCode("GLOBAL_INTERNAL_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR, "An system error has occurred!");

    private String errorCode;
    private HttpStatus httpStatus;
    private String defaultMessage;

    protected ServiceErrorCode(String errorCode, HttpStatus httpStatus, String defaultMessage) {
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return defaultMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
