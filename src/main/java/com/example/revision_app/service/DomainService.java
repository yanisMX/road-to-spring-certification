package com.example.revision_app.service;

import com.example.revision_app.model.Domain;

import java.util.List;

public interface DomainService {

    List<Domain> findAll();

    Domain findById(Long id);

    Domain save(Domain domain);

    void deleteById(Long id);

    Domain update(Long id, String newName, String newDescription, String username);
}
