package com.towh.identity_service.controller;

import java.util.*;

import com.towh.identity_service.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import com.towh.identity_service.dto.request.ApiResponse;
import com.towh.identity_service.dto.request.UserCreationRequest;
import com.towh.identity_service.dto.request.UserUpdateRequest;
import com.towh.identity_service.entity.User;
import com.towh.identity_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users") // It helps us to create a base URL for all the endpoints in the controller
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    // Add a POST mapping to create a new user
    @PostMapping
    public ApiResponse<?> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();

        // Set the response code and result
        response.setCode(200);
        response.setResult(userService.createUser(request));

        return response;
    }

    @GetMapping
        // Add a GET mapping
    ApiResponse<?> getAllUser() {
        ApiResponse<Map<Integer, List<UserResponse>>> response = new ApiResponse<>();

        // Get the current authentication
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthentication -> log.info("Role: {}",
                grantedAuthentication.getAuthority()));

        // Set the response code and result
        response.setCode(200);
        response.setResult(Collections.singletonMap(userService.getAllUser().size(), userService.getAllUser()));
        return response;
    }

    @GetMapping("/{userId}")
    ApiResponse<?> getUserById(@PathVariable("userId") String userId) {
        log.info("In method get user by id");
        ApiResponse<UserResponse> response = new ApiResponse<>();

        // Set the response code and result
        response.setCode(200);
        response.setResult(userService.getUserById(userId));

        return response;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getCurrentUserInfor() {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.getCurrentUserInfo())
                .build();
    }

    @PutMapping("{userId}")
    UserResponse putMethodName(@PathVariable("userId") String id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("{userId}")
    String deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User deleted successfully";
    }


}
