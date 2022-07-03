package com.misiontic.ciclo4.security.services;

import com.misiontic.ciclo4.security.models.AppUser;

public interface AppUserService {

  AppUser findUserById(String userId);

  void addUser(AppUser user);
}
