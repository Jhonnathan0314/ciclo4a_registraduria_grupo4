package com.misiontic.ciclo4.security.controllers;

import java.util.List;

import com.misiontic.ciclo4.security.models.Permission;
import com.misiontic.ciclo4.security.services.PermissionsService;

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
@RequestMapping("/permission")
public class PermissionResource {

    @Autowired
    private PermissionsService permissionService;

    @GetMapping("/get")
    public ResponseEntity<Permission> findById(String permissionId){
        return ResponseEntity.ok().body(permissionService.find(permissionId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Permission>> findAll(){
       return ResponseEntity.ok().body(permissionService.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public Permission create(@RequestBody Permission permission){
        return permissionService.addPermission(permission);
    }

    @PutMapping("/update")
    public Permission update(@RequestBody String permissionId, Permission permission){
       return permissionService.updatePermission(permissionId, permission);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public void delete(String permissionId){
        permissionService.delete(permissionId);
    }
}
