package com.mintic.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mintic.security.models.Permission;
import com.mintic.security.models.Role;
import com.mintic.security.models.RolePermission;
import com.mintic.security.repositories.PermissionRepository;
import com.mintic.security.repositories.RolePermissionRepository;
import com.mintic.security.repositories.RoleRepository;


@CrossOrigin
@RestController
@RequestMapping("role-permission")
public class RolePermissionController {
	//Declaracion de variables
	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	//Metodos propios
	@GetMapping("")
	public List<RolePermission> index(){
		return this.rolePermissionRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("role/{id_role}/permission/{id_permission}")
	public RolePermission create(@PathVariable String id_role, @PathVariable String id_permission) {
		RolePermission rolePermission = new RolePermission();
		Role role = this.roleRepository.findById(id_role).get();
		Permission permission = this.permissionRepository.findById(id_permission).get();
		if(role != null && permission != null) {
			rolePermission.setRole(role);
			rolePermission.setPermission(permission);
			return this.rolePermissionRepository.save(rolePermission);
		}else {
			return null;
		}
	}
	
	@GetMapping("{id}")
	public RolePermission show(@PathVariable String id) {
		return this.rolePermissionRepository.findById(id).orElse(null);
	}
	
	@PutMapping("{id_role_permission}/role/{id_role}/permission/{id_permission}")
	public RolePermission update(@PathVariable String id_role_permission, @PathVariable String id_role, 
			@PathVariable String id_permission) {
		RolePermission rolePermission = this.rolePermissionRepository.findById(id_role_permission).orElse(null);
		Role role = this.roleRepository.findById(id_role).get();
		Permission permission = this.permissionRepository.findById(id_permission).get();
		if(rolePermission != null && role != null && permission != null) {
			rolePermission.setRole(role);
			rolePermission.setPermission(permission);
			return this.rolePermissionRepository.save(rolePermission);
		}else {
			return null;
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable String id) {
		RolePermission rolePermission = this.rolePermissionRepository.findById(id).orElse(null);
		if(rolePermission != null) {
			this.rolePermissionRepository.delete(rolePermission);
		}
	}
	
	@PostMapping("validate/role/{id_role}")
	public RolePermission validate(@PathVariable String id_role, @RequestBody Permission info) {
		Permission permission = this.permissionRepository.getPermission(info.getUrl(), info.getMethod());
		Role role = this.roleRepository.findById(id_role).get();
		if(permission != null && role != null) {
			return this.rolePermissionRepository.getRolePermission(role.get_id(), permission.get_id());
		}else {
			return null;
		}
	}
}
