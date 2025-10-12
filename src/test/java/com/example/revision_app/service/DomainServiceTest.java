package com.example.revision_app.service;

import com.example.revision_app.exception.DomainNotFoundException;
import com.example.revision_app.model.AuditLog;
import com.example.revision_app.repository.AuditLogRepository;
import com.example.revision_app.repository.DomainRepository;
import com.example.revision_app.service.impl.DomainServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.revision_app.model.Domain;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Junit 5
public class DomainServiceTest {

    // Crée un faux repository
    @Mock
    private DomainRepository domainRepository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks // Crée le service et injecte les mocks ci-dessus
    private DomainServiceImpl domainService;

    @Test
    void shouldFindById() {
        //Given - Préparer les données + configurer les mocks
        Domain domain = new Domain();
        domain.setId(1L);
        domain.setName("domaine");
        domain.setDescription("description");
        when(domainRepository.findById(1L)).thenReturn(Optional.of(domain));

        //When - Exécuter la méthode à tester
        Domain result =  domainService.findById(1L);

        // Then - Vérifier le résultat
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("domaine");
        assertThat(result.getDescription()).isEqualTo("description");

        // Then - Vérifier les interactions avec le mock
        verify(domainRepository).findById(1L);
    }

    @Test
    void shouldSaveDomain() {
        Domain domain = new Domain();
        domain.setName("domaine");
        domain.setDescription("description");


        when(domainRepository.save(domain)).thenReturn(domain);

        Domain result = domainService.save(domain);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("domaine");
        verify(domainRepository).save(domain);

    }

    @Test
    void shouldReturnAllDomains() {
        List<Domain> domains = List.of(
                new Domain(1L, "Core Spring", "Desc 1"),
                new Domain(2L, "Spring Data", "Desc 2")
        );
        when(domainRepository.findAll()).thenReturn(domains);

        List<Domain> result = domainService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        verify(domainRepository).findAll();

    }

    @Test
    void shouldDeleteDomain() {
        // Given
        when(domainRepository.existsById(1L)).thenReturn(true);
        doNothing().when(domainRepository).deleteById(1L);

        // When
        domainService.deleteById(1L);

        // Then
        verify(domainRepository).existsById(1L);
        verify(domainRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentDomain() {
        // Given
        when(domainRepository.existsById(999L)).thenReturn(false);

        // When + Then
        assertThatThrownBy(() -> domainService.deleteById(999L))
                .isInstanceOf(DomainNotFoundException.class);

        // Then
        verify(domainRepository).existsById(999L);
        verify(domainRepository, never()).deleteById(999L);
    }

    @Test
    void shouldUpdateDomainAndCreateAudit() {
        // Given
        Domain existingDomain = new Domain(1L, "Core Spring", "Old desc");

        String newName = "Spring Toto";
        String newDesc = "New desc";
        String principal = "Toto";

        when(domainRepository.findById(1L)).thenReturn(Optional.of(existingDomain));
        when(domainRepository.save(any(Domain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<AuditLog> auditCaptor = ArgumentCaptor.forClass(AuditLog.class);

        // When
        Domain result = domainService.update(1L, newName, newDesc, principal);

        verify(auditLogRepository).save(auditCaptor.capture());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Spring Toto");
        assertThat(result.getDescription()).isEqualTo("New desc");

        AuditLog savedAudit = auditCaptor.getValue();
        assertThat(savedAudit.getAction()).isEqualTo("UPDATE_DOMAIN");
        assertThat(savedAudit.getUsername()).isEqualTo("Toto");
        assertThat(savedAudit.getEntityType()).isEqualTo("Domain");
        assertThat(savedAudit.getEntityId()).isEqualTo(1L);



        verify(domainRepository).findById(1L);
        verify(domainRepository).save(any(Domain.class));
        verify(auditLogRepository).save(any(AuditLog.class));
    }

    @Test
    void shouldRollbackUpdate() {
        //when(domainRepository.findById(999L)).thenReturn();
    }
}
