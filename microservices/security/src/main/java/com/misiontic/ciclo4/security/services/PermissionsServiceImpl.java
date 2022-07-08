package com.misiontic.ciclo4.security.services;

import java.util.List;

import com.misiontic.ciclo4.security.models.Permission;
import com.misiontic.ciclo4.security.repositories.PermissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionsServiceImpl implements PermissionsService{

    @Autowired
    private PermissionRepository permissionRepo;

    @Override
    public List<Permission> findAll() {
        return permissionRepo.findAll();
    }

    @Override
    public Permission find(String permissionId) {
        return permissionRepo.findById(permissionId).get();
    }

    @Override
    public Permission addPermission(Permission permission) {
        return permissionRepo.save(permission);
    }

    @Override
    public Permission updatePermission(String permissionId, Permission permission) {
        final var currentPermission = permissionRepo.findById(permissionId);
        if(currentPermission == null) return null;
        final var updatedPermission = new Permission(permissionId, permission.url(), permission.method());
        return permissionRepo.save(updatedPermission);
    }

    @Override
    public void delete(String permissionId) {
        final var permission = permissionRepo.findById(permissionId).get();
        if(permission == null) return;
        permissionRepo.delete(permission);
    }
}
