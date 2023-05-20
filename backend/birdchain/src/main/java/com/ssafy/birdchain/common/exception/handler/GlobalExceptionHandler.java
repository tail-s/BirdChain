package com.ssafy.birdchain.common.exception.handler;

import com.ssafy.birdchain.common.exception.CommonApiException;
import com.ssafy.birdchain.common.exception.response.ErrorResponse;
import com.ssafy.birdchain.common.exception.errorcode.CommonErrorCode;
import com.ssafy.birdchain.common.exception.errorcode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(CommonApiException.class)
    public ResponseEntity<Object> handlerCommonApiException(CommonApiException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus())
                .code(errorCode.name())
                .message(message)
                .build();
    }

}