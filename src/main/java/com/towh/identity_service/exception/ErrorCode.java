package com.towh.identity_service.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum ErrorCode {
    // User Management
    USER_EXISTED(1001, "User existed", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),

    // Authentication
    INVALID_CREDENTIALS(1003, "Invalid credentials", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(1004, "Invalid token", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_EXPIRED(1005, "Token expired", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_NOT_FOUND(1006, "Token not found", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_REVOKED(1007, "Token revoked", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REFRESH_TOKEN(1008, "Invalid refresh token", HttpStatus.INTERNAL_SERVER_ERROR),
    REFRESH_TOKEN_EXPIRED(1009, "Refresh token expired", HttpStatus.INTERNAL_SERVER_ERROR),
    REFRESH_TOKEN_NOT_FOUND(1010, "Refresh token not found", HttpStatus.INTERNAL_SERVER_ERROR),

    // User Creation Invalid
    PASSWORD_PATTERN_INVALID(2003,
            "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace", HttpStatus.BAD_REQUEST),
    FIRST_NAME_INVALID(2004, "First name is required", HttpStatus.BAD_REQUEST),
    LAST_NAME_INVALID(2005, "Last name is required", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(2006, "Email is Wrong Format", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(2001, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2007, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    DOB_INVALID(2008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    // Uncategorized
    INVALID_KEY(9998, "Invalid Key", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exceptions", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    SIGNER_KEY_UNAUTHENTICATED(1008, "Signer Key Unauthenticated", HttpStatus.UNAUTHORIZED);

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
