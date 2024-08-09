package com.towh.identity_service.mapper;


import com.towh.identity_service.dto.request.RoleRequest;
import com.towh.identity_service.dto.response.RoleResponse;
import com.towh.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissionSet", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
