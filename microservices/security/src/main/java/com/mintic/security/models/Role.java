package com.mintic.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("role")
public class Role {
	//Declaracion de variables
	@Id
	private String _id;
	private String name;
	private String description;
	
	//Metodo constructor
	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Role() {
		
	}

	//Metodos get y set
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
