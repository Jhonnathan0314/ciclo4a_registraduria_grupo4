package com.mintic.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("rolePermission")
public class RolePermission {
	//Declaracion de variables
	@Id
	private String id;
	@DBRef
	private Role role;
	@DBRef
	private Permission permission;
	
	//Metodo constructor
	public RolePermission() {
		
	}

	//Metodos get y set
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
