package com.klarna.domain.exception;

public class PersonNumberAlreadyExists extends RuntimeException {

    public PersonNumberAlreadyExists(String personNumber) {
        super("person with number:" + personNumber + " already exists!");
    }

}
