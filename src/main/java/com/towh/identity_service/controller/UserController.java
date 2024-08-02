package com.towh.identity_service.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.towh.identity_service.dto.request.ApiResponse;
import com.towh.identity_service.dto.request.UserCreationRequest;
import com.towh.identity_service.dto.request.UserUpdateRequest;
import com.towh.identity_service.entity.User;
import com.towh.identity_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // It helps us to create a base URL for all the endpoints in the controller
public class UserController {
    @Autowired
    private UserService userService;

    // Add a POST mapping to create a new user
    @PostMapping
    public ApiResponse<?> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();

        // Set the response code and result
        response.setCode(200);
        response.setResult(userService.createUser(request));

        return response;
    }

    @GetMapping // Add a GET mapping
    ApiResponse<?> getAllUser() {
        ApiResponse<Map<Integer, List<User>>> response = new ApiResponse<>();

        // Set the response code and result
        response.setCode(200);
        response.setResult(Collections.singletonMap(userService.getAllUser().size(), userService.getAllUser()));
        return response;
    }

    @GetMapping("/{userId}")
    ApiResponse<?> getUserById(@PathVariable("userId") String userId) {
        ApiResponse<User> response = new ApiResponse<>();

        // Set the response code and result
        response.setCode(200);
        response.setResult(userService.getUserById(userId));

        return response;
    }

    @PutMapping("{userId}")
    User putMethodName(@PathVariable("userId") String id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("{userId}")
    String deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User deleted successfully";
    }
}
