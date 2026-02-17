package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class, OrderNotFoundException.class})
    public ResponseEntity<ErrorResponse> entityNotFoundException(Exception e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ErrorResponse.builder().timestamp(now()).message(message).error(status.getReasonPhrase()).build());
    }
}
