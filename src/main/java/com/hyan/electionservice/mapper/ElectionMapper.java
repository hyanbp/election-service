package com.hyan.electionservice.mapper;

import com.hyan.electionservice.entity.Election;

import java.time.LocalDateTime;

public class ElectionMapper {

    public static Election from(String name, Integer expirationMinutes){
        return Election.Builder.of()
                .expirationMinutes(expirationMinutes == null ? 1 : expirationMinutes)
                .name(name)
                .no(0)
                .yes(0)
                .openElection(LocalDateTime.now())
                .build();
    }
}
