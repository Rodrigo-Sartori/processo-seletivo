package com.desafio.agenda.config.exception;

public class ContactListNotFoundException extends RuntimeException {
    public ContactListNotFoundException(String message) {
        super(message);
    }
}