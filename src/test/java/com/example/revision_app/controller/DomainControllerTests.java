package com.example.revision_app.controller;

import com.example.revision_app.model.Domain;
import com.example.revision_app.service.DomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = DomainController.class) // Charge uniquement le controller
public class DomainControllerTests {

    @MockitoBean
    DomainService domainService;

    /*
    Spring Boot configure automatiquement ObjectMapper importé par la lib Jackson ->
    permet de serializer/deserializer
    objet <-> Json
    writeValueAsString(obj)Objet → JSON (String)objectMapper.writeValueAsString(domain)
    readValue(json, Class)JSON → ObjetobjectMapper.readValue(json, Domain.class)
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnDomain() throws Exception {
        Domain existingDomain = new Domain(1L, "Core Spring", "Old desc");
        when(domainService.findById(1L)).thenReturn(existingDomain);

        mockMvc.perform(get("/api/domaines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Core Spring"))
                .andExpect(jsonPath("$.description").value("Old desc"));
    }

    @Test
    void shouldReturnMultiplesDomain() throws Exception {
        Domain domain1 = new Domain(1L, "Spring1", "Desc1");
        Domain domain2 = new Domain(2L, "Spring2", "Desc2");
        List<Domain> domains = Stream.of(domain1, domain2).toList();
        when(domainService.findAll()).thenReturn(domains);

        mockMvc.perform(get("/api/domaines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Spring1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Spring2"));
    }

    @Test
    void shouldReturnDomainCreated() throws Exception {
        Domain domain = new Domain(null,"Spring","Description");
        Domain domainSaved = new Domain(1L,"Spring","Description");
        when(domainService.save(any(Domain.class))).thenReturn(domainSaved);

        mockMvc.perform(post("/api/domaines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(domain)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Spring"))
                .andExpect(jsonPath("$.description").value("Description"));

        verify(domainService).save(any(Domain.class));
    }

    @Test
    void shouldUpdateDomain() {
        Domain domainSaved = new Domain(1L,"Spring","Description");
        when(domainService.save(any(Domain.class))).thenReturn(domainSaved);
    }

    @Test
    void shouldDeleteDomain() throws Exception {
        mockMvc.perform(delete("/api/domaines/1"))
                .andExpect(status().isNoContent());

        verify(domainService).deleteById(1L);
    }
}
