package com.exalt.company.exception;

import org.springframework.http.HttpStatus;

public enum ErrorEnums {
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    ID_IS_AUTO_GENERATE("the ID is Auto Increment", HttpStatus.BAD_REQUEST);

    private String message;
    private HttpStatus status;

    ErrorEnums(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

}