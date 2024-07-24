package com.towh.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.towh.identity_service.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle the RuntimeException
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException e) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(1001);
        response.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(response);

    }

    // Handle the MethodArgumentNotValidException
    @SuppressWarnings("null")
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(1002);
        response.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}