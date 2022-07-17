package com.mintic.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("permission")
public class Permission {
	//Declaracion de variables
	@Id
	private String _id;
	private String url;
	private String method;
	
	//Metodo constructor	
	public Permission(String url, String method) {
		super();
		this.url = url;
		this.method = method;
	}
	
	public Permission() {
		
	}

	//Metodos get y set
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
