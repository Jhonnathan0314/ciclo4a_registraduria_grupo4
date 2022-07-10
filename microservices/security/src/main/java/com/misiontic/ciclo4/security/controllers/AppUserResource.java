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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

  @GetMapping("/searchByUsername")
  public ResponseEntity<List<AppUser>> searchByUsername(String username){
    return ResponseEntity.ok().body(userService.searchByUsername(username));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public AppUser create(@RequestBody AppUser user){
    return userService.addUser(user);
  }

  @PutMapping("/update")
  public AppUser update(String userId, @RequestBody AppUser user){
    return userService.updateUser(userId, user);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/delete")
  public void delete(String userId){
    userService.delete(userId);
  }

  @PutMapping("/addRole")
  public AppUser addRoleToUser(String userId, String roleId){
    return userService.addRoleToUser(userId, roleId);
  }

  @GetMapping("/hasAccessToRoute")
  public ResponseEntity<Boolean> hasAccessToRoute(String token, String path, String method){
    final var algorithm = Algorithm.HMAC256("secret".getBytes());
    final var verifier = JWT.require(algorithm).build();
    final var decoded = verifier.verify(token);
    final var hasAccess = userService.checkUserPermissionAccessPath(decoded.getSubject(), path, method);
    if(hasAccess)
      return ResponseEntity.ok().body(true);
    else
      return ResponseEntity.status(401).build();
  }
}
