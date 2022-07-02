package com.misiontic.ciclo4.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misiontic.ciclo4.security.models.AppUser;
import com.misiontic.ciclo4.security.services.AppUserService;

@RestController
@RequestMapping("/api")
public class AppUserResource {

  @Autowired
  private AppUserService userService;

  @GetMapping("/user")
  public ResponseEntity<AppUser> findById(String userId) {
    return ResponseEntity.ok().body(userService.findUserById(userId));
  }
}
