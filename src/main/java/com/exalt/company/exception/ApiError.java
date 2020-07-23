package com.exalt.company.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ApiError {
    private String message;
    private String uri;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private Date timestamp;

    public ApiError() {
        this.timestamp = new Date();
    }

    public ApiError(String message, String uri) {
        this();
        this.message = message;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
