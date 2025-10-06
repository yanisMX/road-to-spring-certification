package com.example.revision_app.repository;

import com.example.revision_app.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Différence entre Spring JDBC et Spring Data JPA :
 * Spring JDBC → Tu écris les requêtes SQL toi-même avec JdbcTemplate.
 *               Tu as un contrôle total sur la base de données,
 *               mais tu dois gérer le SQL et le mapping des objets.
 *
 * Spring Data JPA → Basé sur JPA/Hibernate.
 *                   Les requêtes SQL sont générées automatiquement à partir
 *                   des classes d'entité et des interfaces Repository.
 *                   Tu manipules des objets Java plutôt que du SQL.
 *
 *    En étendant JpaRepository<Domain, Long>, cette interface utilise
 *    Spring Data JPA, ce qui signifie que les opérations CRUD (save, findAll, delete, etc.)
 *    sont gérées automatiquement sans écrire de SQL.
 */

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
}
