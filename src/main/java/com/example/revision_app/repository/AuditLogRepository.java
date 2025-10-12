package com.example.revision_app.repository;

import com.example.revision_app.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Trouver tous les logs d'une entité spécifique
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, Long entityId);

    // Trouver les logs d'un utilisateur
    List<AuditLog> findByUsername(String username);

    // Trouver les logs dans une période
    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
