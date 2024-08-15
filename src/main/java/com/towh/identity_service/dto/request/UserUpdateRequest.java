package com.towh.identity_service.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.towh.identity_service.validator.DobConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;
    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;

    List<String> roles;
}