package com.misiontic.ciclo4.security.repositories;

import com.misiontic.ciclo4.security.models.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * RoleRepository
 */
public interface RoleRepository extends MongoRepository<Role, String>{}
