package com.dms.inventory.exception;

import org.springframework.http.HttpStatus;


public class CustomeServiceException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public CustomeServiceException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public CustomeServiceException(String s, String message, HttpStatus status) {
        super(s);
        this.message = message;
        this.status = status;
    }

    public CustomeServiceException(String s, Throwable throwable, String message, HttpStatus status) {
        super(s, throwable);
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
