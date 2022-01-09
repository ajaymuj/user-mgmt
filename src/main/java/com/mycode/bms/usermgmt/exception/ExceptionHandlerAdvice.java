package com.mycode.bms.usermgmt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { InvalidUserException.class })
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity handleException(InvalidUserException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(createErrorResponse(e.getHttpStatus(), e));
    }

    @ExceptionHandler(value = { DuplicateUsernameException.class })
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity handleException(DuplicateUsernameException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(createErrorResponse(e.getHttpStatus(), e));
    }

    @ExceptionHandler(value = { InvalidUserTypeException.class })
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity handleException(InvalidUserTypeException ie) {
        return ResponseEntity.status(ie.getHttpStatus()).body(createErrorResponse(ie.getHttpStatus(), ie));
    }

    @ExceptionHandler(value = { InvalidUserTokenException.class })
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity handleException(InvalidUserTokenException te) {
        return ResponseEntity.status(te.getHttpStatus()).body(createErrorResponse(te.getHttpStatus(), te));
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }

    static public String generateRandomErrorCode() {
        return UUID.randomUUID().toString();
    }

    public ErrorResponse createErrorResponse(HttpStatus httpStatus, Exception e) {
        Throwable rootCause = getRootCause(e);
        String rootErrorMsg = rootCause == e ? null : rootCause.getMessage();
        String logRefCode = generateRandomErrorCode();
        log.error("errorRefCode: {}", logRefCode, e);

        return new ErrorResponse(httpStatus.value(),
                ServiceErrorCode.GLOBAL_INTERNAL_ERROR.getErrorCode(),
                e.getMessage(),
                rootErrorMsg,
                null,
                logRefCode);
    }

}
