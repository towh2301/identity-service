package com.towh.identity_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.towh.identity_service.entity.User;

import javax.swing.text.html.Option;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username); // Check if a user with the given username exists

    Boolean existsByEmail(String email); // Check if a user with the given email exist

    Optional<User> findByEmail(String email); // Find a user by their email

    Optional<User> findByUsername(String username);
}