package com.mintic.security.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mintic.security.models.User;


public interface UserRepository extends MongoRepository<User, String> {

	@Query("{'email': ?0}")
    public User findByEmail(String email);
	
}