package com.misiontic.ciclo4.security.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

import com.misiontic.ciclo4.security.models.AppUser;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

  @Query(value = "{'username': ?0}")
  AppUser findByUserName(String username);

  @Query(value = "{'username': {$regex: /.*?0.*/} }")
  List<AppUser> searchByUsername(String username);
}
