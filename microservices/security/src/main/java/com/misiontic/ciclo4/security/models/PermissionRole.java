package com.misiontic.ciclo4.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PermissionRole")
public record PermissionRole(
    @Id String id,
    @DBRef Role role,
    @DBRef Permission permission
) {
}