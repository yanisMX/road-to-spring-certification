package com.example.revision_app.controller;

import com.example.revision_app.model.Domain;
import com.example.revision_app.service.DomainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@RestController : Combine @Controller + @ResponseBody (retourne JSON)
@RequestMapping : Préfixe commun pour tous les endpoints
@PathVariable : Extrait une variable de l'URL (/api/domaines/{id})
@RequestBody : Convertit le JSON reçu en objet Java
@Valid : Active la validation Bean Validation sur l'objet
*/

@RestController
@RequestMapping("/api/domaines")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    @GetMapping
    public ResponseEntity<List<Domain>> getAllDomains() {
        List<Domain> domains = domainService.findAll();
        return ResponseEntity.ok(domains);  // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domain> getDomainById(@PathVariable Long id) {
        Domain domain = domainService.findById(id);
        return ResponseEntity.ok(domain);  // 200 OK
    }

    @PostMapping
    public ResponseEntity<Domain> createDomain(@Valid @RequestBody Domain domain) {
        Domain createdDomain = domainService.save(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDomain);  // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Domain> updateDomain(
            @PathVariable Long id,
            @Valid @RequestBody Domain domain
    ) {
        Domain updatedDomain = domainService.update(id, domain);
        return ResponseEntity.ok(updatedDomain);  // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable Long id) {
        domainService.deleteById(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}