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
    public User postCreateUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setCode(1000);
        response.setResult(userService.createUser(request));

        return response.getResult();
    }

    @GetMapping
    List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{userId}")
    User getUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
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
