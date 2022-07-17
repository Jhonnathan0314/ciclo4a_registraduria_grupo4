package com.mintic.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("user")
public class User {
	//Declaracion de variables
	@Id
	private String _id;
	private String pseudonym;
	private String email;
	private String password;
	
	@DBRef
	private Role role;
	
	//Metodo constructor
	public User(String pseudonym, String email, String password) {
		super();
		this.pseudonym = pseudonym;
		this.email = email;
		this.password = password;
	}

	public User() {
		
	}

	//Metodos get y set
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
