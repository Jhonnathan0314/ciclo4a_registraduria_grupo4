package com.misiontic.ciclo4.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.misiontic.ciclo4.security.models.AppUser;
import com.misiontic.ciclo4.security.services.AppUserServiceImpl;

@SpringBootApplication
public class SecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(AppUserServiceImpl service) {
    Collection<String> sampleRoles = new ArrayList<>();
    sampleRoles.add("admin");
    sampleRoles.add("user");
    return args -> {
      service.addUser(
          new AppUser("001", "userOne", "email@email.com", "pass",
              sampleRoles));
      service.addUser(new AppUser("002", "userTwo", "emailqemail.com",
          "pass", sampleRoles));
    };
  }
}
