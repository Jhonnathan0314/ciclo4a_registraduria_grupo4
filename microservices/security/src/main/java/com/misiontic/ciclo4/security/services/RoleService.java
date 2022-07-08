package com.misiontic.ciclo4.security.services;

import java.util.List;

import com.misiontic.ciclo4.security.models.PermissionRole;
import com.misiontic.ciclo4.security.models.Role;

/**
 * RoleService
 */
public interface RoleService {

    List<Role> findAll();
    Role findRoleById(String id);
    Role addRole(Role role);
    Role updateRole(String roleId, Role role);
    void deleteRole(String roleId);
    PermissionRole addPermissionToRole(String roleId, String privilegeId);
}
