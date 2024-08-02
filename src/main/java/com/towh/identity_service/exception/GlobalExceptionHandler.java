package com.towh.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.towh.identity_service.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle the RuntimeException
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException e) {
        ApiResponse<?> response = new ApiResponse<>();

        // Set the error code and message to uncategorized
        response.setCode(ErrorCode.UNCATEGORIZED.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException e) {
        ApiResponse<?> response = new ApiResponse<>();
        ErrorCode errorCode = e.getErrorCode();

        // Get the error code and message from the exception
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    // Handle the MethodArgumentNotValidException
    @SuppressWarnings("null")
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<?> response = new ApiResponse<>();
        String enumKey = e.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {
            // To-do: Handle the exception

        }

        response.setCode(ErrorCode.valueOf(enumKey).getCode());
        response.setMessage(ErrorCode.valueOf(enumKey).getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}