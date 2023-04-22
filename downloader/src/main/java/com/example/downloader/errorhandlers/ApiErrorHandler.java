package com.example.downloader.errorhandlers;

import com.example.downloader.exceptions.ApiException;
import com.example.downloader.exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(
        NotFoundException ex
    ) {

        ApiException apiException = ApiException.builder()
            .message(ex.getMessage())
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.NOT_FOUND)
            .throwable(ex)
            .build();

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUnsatisfiedServletRequestParameterException(
        UnsatisfiedServletRequestParameterException ex
    ) {

        ApiException apiException = ApiException.builder()
            .message(ex.getMessage())
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST)
            .throwable(ex)
            .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
