package com.misiontic.ciclo4.security.services;

import java.util.List;

import com.misiontic.ciclo4.security.models.Permission;

public interface PermissionsService {

    List<Permission> findAll();
    Permission find(String permissionId);
    Permission addPermission(Permission permission);
    Permission updatePermission(String permissionId, Permission permission);
    void delete(String permissionId);
}
