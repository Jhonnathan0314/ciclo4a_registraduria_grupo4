package com.misiontic.ciclo4.security.repositories;

import java.util.List;

import com.misiontic.ciclo4.security.models.PermissionRole;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PermissionRoleRepository extends MongoRepository<PermissionRole, String>{
}
