package com.hyan.electionservice.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;

public enum DecisionType {

    SIM,
    NAO;


    public static DecisionType get(String name) {
        return EnumSet.allOf(DecisionType.class)
                .stream()
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetros informados inválidos."));
    }
}
