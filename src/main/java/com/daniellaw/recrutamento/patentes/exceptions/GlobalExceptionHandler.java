package com.daniellaw.recrutamento.patentes.exceptions;

import com.daniellaw.recrutamento.patentes.service.exception.PatentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatentNotFoundException.class)
    public ResponseEntity<StandardError> patentNotFoundException(PatentNotFoundException e, HttpServletRequest request) {

        StandardError err = new StandardError();
        err.setTimeStamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Patent Not Found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(err.getStatus()).body(err);
    }




}
