package com.santer.testFly.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        // Aquí puedes construir un mensaje de error basado en las violaciones
        // Por ejemplo, concatenar todos los mensajes de error en un string
        StringBuilder message = new StringBuilder();
        e.getConstraintViolations().forEach(violation -> message.append(violation.getMessage()).append("\n"));

        // Devolver una respuesta con estado 403 Forbidden
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleConstraintViolationException(IllegalArgumentException e) {
        StringBuilder message = new StringBuilder();
        message.append(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }
}