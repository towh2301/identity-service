package com.towh.identity_service.service;

import java.util.List;

import com.towh.identity_service.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.towh.identity_service.dto.request.*;
import com.towh.identity_service.entity.User;
import com.towh.identity_service.exception.*;
import com.towh.identity_service.mapper.UserMapper;
import com.towh.identity_service.repository.UserRepository;


@Service
@RequiredArgsConstructor // Lombok's annotation to generate a constructor with all final fields
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    // Inject the UserRepository and UserMapper
    UserRepository userRepository;
    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        // Check if the user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Set the user's properties from the request using mapstruct
        User user = userMapper.toUser(request);

        // Hash the user's password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save the user to the database
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String userId) {
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
}
