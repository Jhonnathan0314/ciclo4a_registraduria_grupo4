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
import com.mintic.security.repositories.PermissionRepository;


@CrossOrigin
@RestController
@RequestMapping("permission")
public class PermissionController {
	@Autowired
	private PermissionRepository permissionRepository;
	
	@GetMapping("")
	public List<Permission> index() {
		return this.permissionRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Permission create(@RequestBody Permission info) {
		return this.permissionRepository.save(info);
	}
	
	@GetMapping("{id}")
	public Permission show(@PathVariable String id) {
		return this.permissionRepository.findById(id).orElse(null);
	}
	
	@PutMapping("{id}")
	public Permission update(@PathVariable String id, @RequestBody Permission info) {
		Permission permission = this.permissionRepository.findById(id).orElse(null);
		if(permission != null) {
			permission.setUrl(info.getUrl());
			permission.setMethod(info.getMethod());
			return this.permissionRepository.save(permission);
		}else {
			return null;
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable String id) {
		Permission permission = this.permissionRepository.findById(id).orElse(null);
		if(permission != null) {
			this.permissionRepository.delete(permission);
		}
	}
}
