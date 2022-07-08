package com.misiontic.ciclo4.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.misiontic.ciclo4.security.models.AppUser;
import com.misiontic.ciclo4.security.services.AppUserService;

@RestController
@RequestMapping("/user")
public class AppUserResource {

  @Autowired
  private AppUserService userService;

  @GetMapping("/get")
  public ResponseEntity<AppUser> findById(String userId) {
    return ResponseEntity.ok().body(userService.findUserById(userId));
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<AppUser>> findAll(){
    return ResponseEntity.ok().body(userService.findAll());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public AppUser create(@RequestBody AppUser user){
    return userService.addUser(user);
  }

  @PutMapping("/update")
  public AppUser update(@RequestBody String userId, AppUser user){
    return userService.updateUser(userId, user);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/delete")
  public void delete(String userId){
    userService.delete(userId);
  }

  @PutMapping("/addRole")
  public AppUser addRoleToUser(@RequestBody String userId, String roleId){
    return userService.addRoleToUser(userId, roleId);
  }
}
