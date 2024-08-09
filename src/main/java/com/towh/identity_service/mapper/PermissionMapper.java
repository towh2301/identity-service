package com.towh.identity_service.mapper;

import com.towh.identity_service.dto.request.PermissionRequest;
import com.towh.identity_service.dto.request.UserCreationRequest;
import com.towh.identity_service.dto.request.UserUpdateRequest;
import com.towh.identity_service.dto.response.PermissionResponse;
import com.towh.identity_service.dto.response.UserResponse;
import com.towh.identity_service.entity.Permission;
import com.towh.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
