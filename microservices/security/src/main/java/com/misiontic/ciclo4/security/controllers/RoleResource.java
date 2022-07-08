package com.misiontic.ciclo4.security.controllers;

import com.misiontic.ciclo4.security.models.Role;
import com.misiontic.ciclo4.security.services.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleResource {

    @Autowired
    private RoleService roleService;

    @GetMapping("/find")
    public ResponseEntity<Role> findById(String roleId){
        return ResponseEntity.ok().body(roleService.findRoleById(roleId));
    }

}
