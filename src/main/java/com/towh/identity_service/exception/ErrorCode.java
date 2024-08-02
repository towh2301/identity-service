package com.towh.identity_service.exception;

public enum ErrorCode {
    // User Management
    USER_EXISTED(1001, "User existed"),
    USER_NOT_FOUND(1002, "User not found"),

    // Authentication
    INVALID_CREDENTIALS(1003, "Invalid credentials"),
    INVALID_TOKEN(1004, "Invalid token"),
    TOKEN_EXPIRED(1005, "Token expired"),
    TOKEN_NOT_FOUND(1006, "Token not found"),
    TOKEN_REVOKED(1007, "Token revoked"),
    INVALID_REFRESH_TOKEN(1008, "Invalid refresh token"),
    REFRESH_TOKEN_EXPIRED(1009, "Refresh token expired"),
    REFRESH_TOKEN_NOT_FOUND(1010, "Refresh token not found"),

    // User Creation Invalid
    USERNAME_INVALID(2001, "Username is at least 3 characters"),
    PASSWORD_INVALID(2002, "Password must be at least 8 characters long"),
    PASSWORD_PATTERN_INVALID(2003,
            "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace"),
    FIRST_NAME_INVALID(2004, "First name is required"),
    LAST_NAME_INVALID(2005, "Last name is required"),
    EMAIL_INVALID(2006, "Email is Wrong Format"),
    DOB_INVALID(2007, "Date of birth should be in the past"),

    // Uncategorized
    INVALID_KEY(9998, "Invalid Key"),
    UNCATEGORIZED(9999, "Uncategorized Exceptions"),

    ; // Default error code for unexpected exceptions

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
