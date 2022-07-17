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

import com.mintic.security.models.Role;
import com.mintic.security.repositories.RoleRepository;


@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("")
	public List<Role> index(){
		return this.roleRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Role create(@RequestBody Role info) {
		return this.roleRepository.save(info);
	}
	
	@GetMapping("{id}")
	public Role show(@PathVariable String id) {
		return this.roleRepository.findById(id).orElse(null);
	}
	
	@PutMapping("{id}")
	public Role update(@PathVariable String id, @RequestBody Role info) {
		Role role = this.roleRepository.findById(id).orElse(null);
		if(role != null) {
			role.setName(info.getName());
			role.setDescription(info.getDescription());
			return this.roleRepository.save(role);
		}else {
			return null;
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable String id) {
		Role role = this.roleRepository.findById(id).orElse(null);
		if(role != null) {
			this.roleRepository.delete(role);
		}
	}
}
