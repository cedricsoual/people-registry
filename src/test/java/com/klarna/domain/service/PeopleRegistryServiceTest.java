package com.klarna.domain.service;

import com.klarna.domain.exception.PersonNotFoundException;
import com.klarna.domain.exception.PersonNumberAlreadyExists;
import com.klarna.domain.model.ChildIdentity;
import com.klarna.domain.model.PersonIdentity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PeopleRegistryServiceTest {

    @Test
    public void savePersonWhenAlreadyExists() {
        PeopleRegistryService service = new PeopleRegistryService(new PersonRepository());
        service.save(new PersonIdentity("123", "James", List.of(new ChildIdentity("JamesJunior", 12))));
        Assertions.assertThrows(PersonNumberAlreadyExists.class, () -> service.save(new PersonIdentity("123", "James", List.of(new ChildIdentity("JamesJunior", 12)))));
    }

    @Test
    void findByPersonNumber() {
        PeopleRegistryService service = new PeopleRegistryService(new PersonRepository());
        service.save(new PersonIdentity("123", "James", List.of(new ChildIdentity("JamesJunior", 12))));
        PersonIdentity actual = service.findByPersonNumber("123");
        Assertions.assertAll(
                () -> Assertions.assertEquals("James", actual.name()),
                () -> Assertions.assertEquals("JamesJunior", actual.children().get(0).name()));
    }

    @Test
    void findByPersonNumberWhenNotFound() {
        PeopleRegistryService service = new PeopleRegistryService(new PersonRepository());
        Assertions.assertThrows(PersonNotFoundException.class, () -> service.findByPersonNumber("123"));
    }

    @Test
    void oldestChildNameByName() {
        PeopleRegistryService service = new PeopleRegistryService(new PersonRepository());
        service.save(new PersonIdentity("123", "James", List.of(new ChildIdentity("JamesJunior", 5), new ChildIdentity("JamesSenior", 10))));
        PersonIdentity actual = service.oldestChildNameByName("123");
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, actual.children().size()),
                () -> Assertions.assertEquals("JamesJunior", actual.children().get(0).name()));
    }

}