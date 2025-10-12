package com.example.revision_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domain")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domain {


    // GeneratedValue pour que l'id soit incrémenté par la base
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domain_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "Le nom du domaine est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Topic> topics = new HashSet<>();

    public Domain(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topics = new HashSet<>();
    }

}
