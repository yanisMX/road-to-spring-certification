package com.example.revision_app.repository;

import com.example.revision_app.model.Domain;
import com.example.revision_app.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class TopicRepositoryTests {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private DomainRepository domainRepository;

    private Domain testDomain;

    @BeforeEach
    void setUp() {
        Domain testDomain = domainRepository.save(new Domain(null, "Spring Framework", "Spring Core concepts"));
    }

    @Test
    void shouldCreateTopic(){

        Topic topicToSave = new Topic();
        topicToSave.setName("Annotation for testing repository");
        topicToSave.setDescription("@DataJpaTest allows us to test repository");
        topicToSave.setDomain(testDomain);

        Topic saved = topicRepository.save(topicToSave);

        assertThat(saved).isNotNull();
        assertThat(saved.getTopicId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Annotation for testing repository");
        assertThat(saved.getDescription()).isEqualTo("@DataJpaTest allows us to test repository");
        assertThat(saved.getDomain()).isEqualTo(testDomain);
    }
}
