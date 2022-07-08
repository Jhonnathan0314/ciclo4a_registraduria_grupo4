package com.misiontic.ciclo4.security.services;

import java.util.List;

import com.misiontic.ciclo4.security.models.Role;

/**
 * RoleService
 */
public interface RoleService {

    List<Role> findAll();
    Role findRoleById(String id);
    void addRole(Role role);
    Role updateRole(String roleId, Role role);
    void deleteRole(String roleId);
    Role addPrivilegeToRole(String roleId, String privilegeId);
}
