package com.example.revision_app.repository;

import com.example.revision_app.model.Domain;
import com.example.revision_app.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
     List<Topic> findByDomain(Domain domain);

    Optional<Topic> findByName(String name);

     boolean existsByName(String name);

     long countByDomain(Domain domain);

     void deleteByDomain(Domain domain);

}
