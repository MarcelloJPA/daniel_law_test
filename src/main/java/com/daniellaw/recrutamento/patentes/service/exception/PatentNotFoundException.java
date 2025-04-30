package com.daniellaw.recrutamento.patentes.service.exception;

public class PatentNotFoundException extends RuntimeException {

    public PatentNotFoundException(String msg) {
        super(msg);
    }

    // Construtor com causa (útil para incluir a exceção original)
    public PatentNotFoundException(String msg, Throwable cause) {
        super(msg, cause); // Passa a causa para a superclasse
    }
}