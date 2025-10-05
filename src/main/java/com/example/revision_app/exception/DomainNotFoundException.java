package com.example.revision_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DomainNotFoundException extends RuntimeException {
  public DomainNotFoundException(Long id) {
    super("Domain not found with id: " + id);
  }
}
