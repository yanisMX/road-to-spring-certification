package com.example.revision_app.service;

public interface AuditLogService {

    void logAction(String action, Long entityId, String username);
}
