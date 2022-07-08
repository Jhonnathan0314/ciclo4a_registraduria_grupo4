package com.misiontic.ciclo4.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AppUser")
public record AppUser(
    @Id String id,
    String username,
    String email,
    String password,
    @DBRef Role role) {
}
