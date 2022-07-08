package com.misiontic.ciclo4.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Role")
public record Role(
    @Id String id,
    String name) {
}
