package com.dms.inventory.exception;

import com.dms.inventory.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = {CustomeServiceException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomeServiceException ex, WebRequest request) {
        if (ex.getStatus() == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getStatus().value()), HttpStatus.OK);
        } else if (ex.getStatus() == HttpStatus.BAD_REQUEST) {
            return new ResponseEntity<>(
                    new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}