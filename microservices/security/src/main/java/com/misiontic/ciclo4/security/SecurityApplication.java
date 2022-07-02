package com.misiontic.ciclo4.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.misiontic.ciclo4.security.models.AppUser;
import com.misiontic.ciclo4.security.repositories.AppUserRepository;

@SpringBootApplication
public class SecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(AppUserRepository repository) {
    Collection<String> sampleRoles = new ArrayList<>();
    sampleRoles.add("admin");
    sampleRoles.add("user");
    return args -> {
      repository
          .save(
              new AppUser("userOne", "passworsd", "email@email.com", "mysecretpassword", sampleRoles));
      repository
          .save(new AppUser("UserTwo", "passworsd", "emailqemail.com", "secretpassword", sampleRoles));
    };
  }
}
