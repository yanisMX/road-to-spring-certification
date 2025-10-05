package com.example.revision_app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.revision_app.dto.ErrorResponse;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


/*
@RestControllerAdvice : Combine @ControllerAdvice + @ResponseBody
Renvoie un ErrorResponse pour chaque cas d'erreur dans l'appli

Annotation Lombok @Builder permet de construire l'objet avec une syntaxe fluide

Méthodes de HttpStatus : value() (int), getReasonPhrase() (String)
Méthodes de WebRequest : getDescription() pour obtenir le path de la requête

@ExceptionHandler(NomClasse.class) : Quand cette exception est levée,
elle sera interceptée et un ErrorResponse structuré sera renvoyé
*/


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDomainNotFound(
            DomainNotFoundException ex,
            WebRequest request
    ) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            WebRequest request
    ){
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
