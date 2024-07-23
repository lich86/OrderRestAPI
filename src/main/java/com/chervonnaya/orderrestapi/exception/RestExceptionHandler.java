package com.chervonnaya.orderrestapi.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    private record ErrorResponse(String message, int statusCode, String error, LocalDateTime timestamp) {
    }
}
