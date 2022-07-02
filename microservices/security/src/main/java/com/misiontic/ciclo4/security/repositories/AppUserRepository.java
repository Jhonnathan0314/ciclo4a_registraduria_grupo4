package com.misiontic.ciclo4.security.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.misiontic.ciclo4.security.models.AppUser;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

  @Query(value = "{'userName': ?0}")
  AppUser findByUserName(String userName);
}
