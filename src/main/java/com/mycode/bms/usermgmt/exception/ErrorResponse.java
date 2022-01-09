package com.mycode.bms.usermgmt.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorResponse {

    private int httpStatus;
    private String errorCode;
    private String message;
    private String rootCause;
    private String stackTrace;
    private String logRefCode;
    private List<FieldMessage> failedFields;

    public ErrorResponse(int httpStatus, String errorCode, String message, String rootCause, String stackTrace, String logRefCode) {
        this(httpStatus, errorCode, message, rootCause, stackTrace, logRefCode, null);
    }

    public ErrorResponse(int httpStatus, String errorCode, String message, String rootCause, String stackTrace, String logRefCode, List<FieldMessage> failedFields) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
        this.rootCause = rootCause;
        this.stackTrace = stackTrace;
        this.logRefCode = logRefCode;
        if (failedFields == null) {
            this.failedFields = Collections.emptyList();
        } else {
            this.failedFields = failedFields;
        }
    }

    public class FieldMessage {
        String name;
        String message;

        public FieldMessage(String name, String message) {
            this.name = name;
            this.message = message;
        }
    }
}
