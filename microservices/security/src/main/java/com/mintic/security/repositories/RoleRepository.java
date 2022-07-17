package com.mintic.security.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mintic.security.models.Role;


public interface RoleRepository extends MongoRepository<Role, String> {

}
