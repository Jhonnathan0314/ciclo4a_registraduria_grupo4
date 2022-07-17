package com.mintic.security.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mintic.security.models.Permission;


public interface PermissionRepository extends MongoRepository<Permission, String> {

	@Query("{'url': ?0, 'method': ?1}")
    Permission getPermission(String url, String method);
	
}
