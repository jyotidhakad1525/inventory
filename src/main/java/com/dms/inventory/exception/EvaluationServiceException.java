package com.dms.inventory.exception;

import org.springframework.http.HttpStatus;

public class EvaluationServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    public EvaluationServiceException() {
        super();
    }

    public EvaluationServiceException(String s) {
        super(s);
    }

    public EvaluationServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EvaluationServiceException(String s, HttpStatus status) {
        super(s);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
