package com.towh.identity_service.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;

@Data // Auto generate the getters and setters
@NoArgsConstructor // Auto generate the no-args constructor
@AllArgsConstructor // Auto generate the constructor with all the arguments
@Builder // Auto generate the builder pattern
@FieldDefaults(level = AccessLevel.PRIVATE) // Auto Set the Access level to private
public class UserCreationRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "PASSWORD_PATTERN_INVALID")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace")
    String password;

    //@NotBlank(message = "FIRST_NAME_INVALID")
    String firstName;

    //    @NotBlank(message = "LAST_NAME_INVALID")
    String lastName;

    // @NotBlank(message = "EMAIL_INVALID")
    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "DOB_INVALID")
    LocalDate dob;
}