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

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

  @Autowired
  private AppUserRepository userRepo;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    final AppUser user = userRepo.findByUserName(username);
    if (user == null)
      throw new UsernameNotFoundException("User not found");
    return new User(user.username(), user.password(), authorities);
  }
}
