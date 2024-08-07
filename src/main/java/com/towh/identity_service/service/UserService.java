package com.towh.identity_service.service;

import java.util.HashSet;
import java.util.List;

import com.towh.identity_service.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.towh.identity_service.dto.request.*;
import com.towh.identity_service.entity.User;
import com.towh.identity_service.enums.Roles;
import com.towh.identity_service.exception.*;
import com.towh.identity_service.mapper.UserMapper;
import com.towh.identity_service.repository.UserRepository;

@Service
@RequiredArgsConstructor // Lombok's annotation to generate a constructor with all final fields
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    // Inject the UserRepository and UserMapper
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request) {
        // Check if the user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Set the user's properties from the request using mapstruct
        User user = userMapper.toUser(request);

        // Hash the user's password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set roles for user
        HashSet<String> rolesHashSet = new HashSet<>();
        rolesHashSet.add(Roles.USER.name());
        user.setRoles(rolesHashSet);

        // Save the user to the database
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser() {
        log.info("In method get all users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String userId) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public UserResponse getCurrentUserInfo(){
        // Get name from auth context holder
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        // Find User by username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }
}
