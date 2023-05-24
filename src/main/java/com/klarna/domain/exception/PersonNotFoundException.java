package com.klarna.domain.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String personNumber) {
        super("person with number:" + personNumber + " not found!");
    }

}
