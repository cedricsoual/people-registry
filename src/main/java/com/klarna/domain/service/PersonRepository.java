package com.klarna.domain.service;

import com.klarna.domain.exception.PersonNotFoundException;
import com.klarna.domain.exception.PersonNumberAlreadyExists;
import com.klarna.domain.model.PersonIdentity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PersonRepository {

    private final Map<String, PersonIdentity> cache;

    public PersonRepository() {
        cache = new ConcurrentHashMap<>();
    }

    public void save(PersonIdentity person) {
        if (cache.containsKey(person.socialNumber())) throw new PersonNumberAlreadyExists(person.socialNumber());
        cache.put(person.socialNumber(), person);
    }

    public PersonIdentity findByPersonNumber(String personNumber) {
        return Optional.ofNullable(cache.get(personNumber))
                .orElseThrow(() -> new PersonNotFoundException(personNumber));
    }

}
