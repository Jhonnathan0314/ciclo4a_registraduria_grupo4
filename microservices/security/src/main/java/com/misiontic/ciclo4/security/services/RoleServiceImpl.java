package com.misiontic.ciclo4.security.services;

import java.util.List;

import com.misiontic.ciclo4.security.models.Role;
import com.misiontic.ciclo4.security.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Role addRole(Role role) {
       return roleRepo.save(role);
    }

    @Override
    public Role findRoleById(String id) {
        return roleRepo.findById(id).get();
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role updateRole(String roleId, Role role) {
        final var currentRole = roleRepo.findById(roleId);
        if(currentRole == null) return null;
        final var updatedRole = new Role(roleId, role.name());
        return roleRepo.save(updatedRole);
    }

    @Override
    public void deleteRole(String roleId) {
        final var role = roleRepo.findById(roleId).get();
        if(role == null) return;
        roleRepo.delete(role);
    }

    @Override
    public Role addPrivilegeToRole(String roleId, String privilegeId) {
        return null;
    }
}
