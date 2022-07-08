package com.misiontic.ciclo4.security.repositories;

import com.misiontic.ciclo4.security.models.PermissionRole;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRoleRepository extends MongoRepository<PermissionRole, String>{

}
