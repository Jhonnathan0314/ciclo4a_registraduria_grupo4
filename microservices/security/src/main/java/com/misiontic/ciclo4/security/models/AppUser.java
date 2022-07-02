package com.misiontic.ciclo4.security.models;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AppUser")
public record AppUser(
    @Id String id,
    String username,
    String email,
    String password,
    Collection<String> roles) {
}
