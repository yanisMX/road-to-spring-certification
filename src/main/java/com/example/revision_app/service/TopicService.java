package com.example.revision_app.service;


import com.example.revision_app.model.Topic;

import java.util.List;

public interface TopicService {

    public Topic findById(Long id);

    public List<Topic> findAll();

    public List<Topic> findByDomainId(Long domainId);

    public Topic create(String name, String description, Long domainId);

    public Topic update(Long id, String name, String description);

    public void delete(Long id);
}
