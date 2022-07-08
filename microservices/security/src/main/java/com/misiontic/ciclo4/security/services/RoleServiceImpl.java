package com.misiontic.ciclo4.security.services;

import com.misiontic.ciclo4.security.models.Role;
import com.misiontic.ciclo4.security.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository repository;

    @Override
    public void addRole(Role role) {
       repository.save(role);
    }

    @Override
    public Role findRoleById(String id) {
        return repository.findById(id).get();
    }

}
