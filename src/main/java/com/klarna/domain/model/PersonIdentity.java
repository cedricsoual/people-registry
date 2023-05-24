package com.klarna.domain.model;

import java.util.List;

public record PersonIdentity(String socialNumber, String name, String spouse, List<ChildIdentity> children) {

    public PersonIdentity(String socialNumber, String name, List<ChildIdentity> children) {
        this(socialNumber, name, null, children);
    }

}
