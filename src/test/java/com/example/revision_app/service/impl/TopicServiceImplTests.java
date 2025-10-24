package com.example.revision_app.service.impl;

import com.example.revision_app.model.Domain;
import com.example.revision_app.model.Topic;
import com.example.revision_app.repository.DomainRepository;
import com.example.revision_app.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Junit 5
class TopicServiceImplTests {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private DomainRepository domainRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    private Domain domain;

    @BeforeEach
    public void setup(){
        domain = new Domain();
        domain.setId(1L);
        domain.setName("domaine");
        domain.setDescription("description");
    }

    @Test
    void shouldFindById(){
        //Given
        Topic topicToFind = new Topic();
        topicToFind.setTopicId(1L);
        topicToFind.setName("newTopic");
        topicToFind.setDescription("newDescription");


        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topicToFind));

        //When
        Topic response = topicService.findById(1L);

        //Then
        assertThat(topicService).isNotNull();
        assertThat(response.getTopicId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("newTopic");
        assertThat(response.getDescription()).isEqualTo("newDescription");

        verify(topicRepository).findById(1L);
    }

    @Test
    void shouldReturnListOfTopic(){
        //Given
        List<Topic> topics = List.of(
                new Topic("topic1", "desc1", domain),
                new Topic("topic2", "desc2", domain)
        );
        when(topicRepository.findAll()).thenReturn(topics);

        //When
        List<Topic> response = topicService.findAll();

        //Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getName()).isEqualTo("topic1");
        assertThat(response.getLast().getName()).isEqualTo("topic2");
    }

    @Test
    void shouldFindByDomainId(){
        //Given
        List<Topic> topics = List.of(
                new Topic("topic1", "desc1", domain),
                new Topic("topic2", "desc2", domain)
        );
        when(domainRepository.findById(anyLong())).thenReturn(Optional.of(domain));
        when(topicRepository.findByDomain(any())).thenReturn(topics);

        //When
        List<Topic> response = topicService.findByDomainId(1L);

        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getName()).isEqualTo("topic1");
        assertThat(response.getLast().getName()).isEqualTo("topic2");
    }

    @Test
    void shouldCreateTopic(){
        //Given
        Topic topic = new Topic("topicToCreate", "Blablabla", domain);
        when(domainRepository.findById(1L)).thenReturn(Optional.ofNullable(domain));
        when(topicRepository.save(any())).thenReturn(topic);

        //When
        Topic response = topicService.create("topicToCreate", "Blablabla", 1L);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("topicToCreate");
        assertThat(response.getDescription()).isEqualTo("Blablabla");
    }

    @Test
    void shouldDeleteTopic(){
        Topic topicToDelete = new Topic("deletedSoon", "descDeleted", domain);
        topicToDelete.setTopicId(1L);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topicToDelete));
        doNothing().when(topicRepository).delete(topicToDelete);

        topicService.delete(1L);

        verify(topicRepository).findById(anyLong());
        verify(topicRepository).delete(any());
    }

    @Test
    void shouldUpdateTopic(){
        //Given
        Topic topic = new Topic("deletedSoon", "descDeleted", domain);
        Topic topicToUpdate = new Topic("updated", "updated", domain);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(topicRepository.save(any())).thenReturn(topicToUpdate);

        //When
        Topic response = topicService.update(1L, topicToUpdate.getName(), topicToUpdate.getDescription());

        assertThat(response).isNotNull();
        assertThat(response.getDescription()).isEqualTo("updated");
        assertThat(response.getName()).isEqualTo("updated");

        verify(topicRepository).findById(anyLong());
        verify(topicRepository).save(any());
    }

}