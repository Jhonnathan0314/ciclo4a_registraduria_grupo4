package com.misiontic.ciclo4.security.repositories;

import com.misiontic.ciclo4.security.models.Permission;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String>{

}
