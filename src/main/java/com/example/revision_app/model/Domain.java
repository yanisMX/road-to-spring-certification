package com.example.revision_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "domaine")
@Data
public class Domain {


    // GeneratedValue pour que l'id soit incrémenté par la base
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "Le nom du domaine est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
