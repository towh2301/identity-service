package com.towh.identity_service.repository;

import com.towh.identity_service.entity.Permission;
import com.towh.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findById(String permissionName);
}
