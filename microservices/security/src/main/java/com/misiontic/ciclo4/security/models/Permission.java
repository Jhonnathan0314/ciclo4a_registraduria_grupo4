package com.misiontic.ciclo4.security.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Permission")
public record Permission(
        @Id String id,
        String url,
        String method
) {
}
