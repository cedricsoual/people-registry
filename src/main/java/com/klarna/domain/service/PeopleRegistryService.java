package com.klarna.domain.service;

import com.klarna.domain.exception.NoChildrenException;
import com.klarna.domain.model.ChildIdentity;
import com.klarna.domain.model.PersonIdentity;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class PeopleRegistryService {

    static Logger logger = Logger.getLogger(PeopleRegistryService.class.getName());

    private final PersonRepository personRepository;

    public PeopleRegistryService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /*
    Endpoint suggestion : POST /identities
     */
    public void save(PersonIdentity personIdentity) {
        personRepository.save(personIdentity);
        logger.info("person with id:" + personIdentity.socialNumber() + " successfully saved!");
    }

    /*
    Endpoint suggestion : GET /identities/:personNumber
    */
    public PersonIdentity findByPersonNumber(String personNumber) {
        return personRepository.findByPersonNumber(personNumber);
    }

    /*
    Endpoint suggestion : GET /identities/:personNumber/oldest-child
    */
    public PersonIdentity oldestChildNameByName(String personNumber) {
        PersonIdentity person = personRepository.findByPersonNumber(personNumber);
        ChildIdentity oldestChild = person.children()
                .stream()
                .min(Comparator.comparingInt(ChildIdentity::age))
                .orElseThrow(NoChildrenException::new);
        return new PersonIdentity(person.socialNumber(), person.name(), List.of(oldestChild));
    }

}