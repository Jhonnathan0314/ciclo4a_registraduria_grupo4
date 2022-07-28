package com.mintic.security.controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.mintic.security.models.User;
import com.mintic.security.repositories.RoleRepository;
import com.mintic.security.repositories.UserRepository;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	//Declaracion de variables
	@Autowired
	private	UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	//Metodos propios
	@GetMapping("")
	public List<User> index() {
		return this.userRepository.findAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public User create(@RequestBody User info) {
		String password = info.getPassword();
		info.setPassword(convertSHA256(password));
		Role role = this.roleRepository.findById("62d9cbf7e7be7361b039f238").orElse(null);
		info.setRole(role);   
		return this.userRepository.save(info);
	}
	
	@GetMapping("{id}")
	public User show(@PathVariable String id) {
		User user = this.userRepository.findById(id).orElse(null);
		return user;
	}
	
	@PutMapping("{id}")
	public User update(@PathVariable String id, @RequestBody User info) {
		User user = this.userRepository.findById(id).orElse(null);
		if(user != null) {
			user.setPseudonym(info.getPseudonym());
			user.setEmail(info.getEmail());
			String password = convertSHA256(info.getPassword());
			user.setPassword(password);
			return this.userRepository.save(user);
		}else {
			return null;
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable String id) {
		User user = this.userRepository.findById(id).orElse(null);
		if(user != null ) {
			this.userRepository.delete(user);
		}
	}
	
	/**
    This method adds an object Role to an object User
    @param String id_user : this params is needed to find the object User
    @param String id_role : this params is needs to find the objet Role
    @return User : this method returns the updated User
  */
	@PutMapping("{id_user}/role/{id_role}")
	public User addRol(@PathVariable String id_user, @PathVariable String id_role) {
		User user = this.userRepository.findById(id_user).orElse(null);
        Role role = this.roleRepository.findById(id_role).orElse(null);
        user.setRole(role);
		return this.userRepository.save(user);			
	}
	
	@PostMapping("validate")
	public User validate(@RequestBody User info, final HttpServletResponse response) throws IOException {
		User user = this.userRepository.findByEmail(info.getEmail());
		String password = convertSHA256(info.getPassword());
		if(user != null && user.getPassword().equals(password)) {
			user.setPassword("");
			return user;
		}else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
	}

	public String convertSHA256(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		for(byte b : hash) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
