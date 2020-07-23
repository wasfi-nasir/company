package com.exalt.company.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionsHandler extends ResponseEntityExceptionHandler {

    final Logger logger = LoggerFactory.getLogger(ApiExceptionsHandler.class);

    @ExceptionHandler(value = {CommonException.class})
    public ResponseEntity<ApiError> resourceNotFoundException(CommonException ex, WebRequest request) {
        logger.error("test the error {}", ex);
        ApiError apiError = new ApiError(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiError, new HttpHeaders(), ex.getError().getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("test the error {}", ex);
        ApiError apiError = new ApiError(ex.getBindingResult().getFieldError().getDefaultMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
