package com.misiontic.ciclo4.security.controllers;

import java.util.List;

import com.misiontic.ciclo4.security.models.Role;
import com.misiontic.ciclo4.security.services.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleResource {

    @Autowired
    private RoleService roleService;

    @GetMapping("/get")
    public ResponseEntity<Role> findById(String roleId){
        return ResponseEntity.ok().body(roleService.findRoleById(roleId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Role>> findAll(){
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public Role create(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @PutMapping("/update")
    public Role update(String roleId,@RequestBody Role role){
        return roleService.updateRole(roleId, role);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void delete(String roleId){
        roleService.deleteRole(roleId);
    }

    @PutMapping("/addPermission")
    public void addPermission(String roleId, String permissionId){
       roleService.addPermissionToRole(roleId, permissionId);
    }
}
