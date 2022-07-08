package com.misiontic.ciclo4.security.services;

import com.misiontic.ciclo4.security.models.Role;

/**
 * RoleService
 */
public interface RoleService {

    void addRole(Role role);
    Role findRoleById(String id);
}
