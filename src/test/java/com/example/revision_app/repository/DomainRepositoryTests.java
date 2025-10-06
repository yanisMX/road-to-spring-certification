package com.example.revision_app.repository;

import com.example.revision_app.model.Domain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

/*
 @DataJpaTest est une annotation de test qui :
        Charge UNIQUEMENT la couche JPA (pas tout le contexte Spring)
        Configure une base de données en mémoire (H2)
        Active @Transactional par défaut (rollback automatique après chaque test)
        Scanne les @Entity et les @Repository
        Ne charge PAS les @Service, @Controller, etc.
 */
@DataJpaTest
public class DomainRepositoryTests {

    @Autowired
    private DomainRepository domainRepository;

    @Test
    void shouldCreateAndFindDomain() {
        Domain domain = new Domain();
        domain.setName("Domain 1");
        domain.setDescription("Domain Description");


        Domain saved = domainRepository.save(domain);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Domain 1");
        assertThat(saved.getDescription()).isEqualTo("Domain Description");
    }

    @Test
    void shouldFindMultipleDomains() {
        Domain domain1 = new Domain();
        domain1.setName("Domain 1");
        domain1.setDescription("Domain Description");

        Domain domain2 = new Domain();
        domain2.setName("Domain 2");
        domain2.setDescription("Domain Description");

        domainRepository.save(domain1);
        domainRepository.save(domain2);

        assertThat(domainRepository.findAll()).hasSize(2);
    }

    @Test
    void shouldDeleteDomain() {
        Domain domain = new Domain();
        domain.setName("Domain 1");
        domain.setDescription("Domain Description");

        domainRepository.save(domain);
        domainRepository.delete(domain);

        assertThat(domainRepository.findById(domain.getId()).isPresent()).isFalse();
    }

}
