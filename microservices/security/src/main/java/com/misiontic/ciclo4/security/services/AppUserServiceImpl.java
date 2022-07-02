package com.misiontic.ciclo4.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misiontic.ciclo4.security.models.AppUser;
import com.misiontic.ciclo4.security.repositories.AppUserRepository;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

  @Autowired
  private AppUserRepository userRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public AppUser findUserById(String userId) {
    return userRepo.findById(userId).get();
  }

  @Override
  public void addUser(AppUser user) {
    final String encodedPassword = passwordEncoder.encode(user.password());
    final var userWithEncodedPassword = new AppUser(
        user.id(),
        user.username(),
        user.email(),
        encodedPassword,
        user.roles());
    userRepo.save(userWithEncodedPassword);
  }
}
