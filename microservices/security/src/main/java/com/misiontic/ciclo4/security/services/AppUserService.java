package com.misiontic.ciclo4.security.services;

import java.util.List;

import com.misiontic.ciclo4.security.models.AppUser;

public interface AppUserService {

  List<AppUser> findAll();
  AppUser findUserById(String userId);
  AppUser addUser(AppUser user);
  AppUser updateUser(String userId, AppUser user);
  void delete(String userId);
  AppUser addRoleToUser(String userId, String roleId);
}
