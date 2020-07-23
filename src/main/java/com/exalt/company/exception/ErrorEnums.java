package com.exalt.company.exception;

import org.springframework.http.HttpStatus;

public enum ErrorEnums {
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    PAGE_INVALID("page > 0", HttpStatus.BAD_REQUEST),
    LIMIT_INVALID("limit > 0", HttpStatus.BAD_REQUEST),
    ID_INVALID("id > 0", HttpStatus.BAD_REQUEST),
    FILE_INVALID("this file does not exist", HttpStatus.NOT_FOUND),
    USERS_NOT_FOUND("there is no candidates in DB", HttpStatus.NOT_FOUND),
    INTERVIEWER_ID_IS_NULL("Enter the Interviewer ID", HttpStatus.NOT_FOUND),
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