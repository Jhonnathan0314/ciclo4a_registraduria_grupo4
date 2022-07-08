package com.misiontic.ciclo4.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.misiontic.ciclo4.security.models.AppUser;
import com.misiontic.ciclo4.security.repositories.AppUserRepository;
import com.misiontic.ciclo4.security.repositories.RoleRepository;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

  @Autowired
  private AppUserRepository userRepo;

  @Autowired
  private RoleRepository roleRepo;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public AppUser findUserById(String userId) {
    return userRepo.findById(userId).get();
  }

  @Override
  public AppUser addUser(AppUser user) {
    final String encodedPassword = passwordEncoder.encode(user.password());
    final var userWithEncodedPassword = new AppUser(user.id(), user.username(), user.email(), encodedPassword,
        user.role());
    return userRepo.save(userWithEncodedPassword);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    final AppUser user = userRepo.findByUserName(username);
    if (user == null)
      throw new UsernameNotFoundException("User not found");
    return new User(user.username(), user.password(), authorities);
  }

  @Override
  public List<AppUser> findAll() {
    return userRepo.findAll();
  }

  @Override
  public AppUser updateUser(String userId, AppUser user) {
    final var currentUser = userRepo.findById(userId).get();
    if(currentUser == null) return null;
    final String encodedPassword = passwordEncoder.encode(user.password());
    final var updatedUser = new AppUser(userId, user.username(), user.email(), encodedPassword, user.role());
    return userRepo.save(updatedUser);
  }

  @Override
  public void delete(String userId) {
    final var currentUser = userRepo.findById(userId).get();
    if(currentUser == null) return;
    userRepo.delete(currentUser);
  }

  @Override
  public AppUser addRoleToUser(String userId, String roleId) {
    final var user = userRepo.findById(userId).get();
    final var role = roleRepo.findById(roleId).get();
    if(user == null || role == null) return null;
    final var updatedUser = new AppUser(userId, user.username(), user.email(), user.password(), role);
    return userRepo.save(updatedUser);
  }
}
