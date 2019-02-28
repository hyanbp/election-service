package com.hyan.electionservice.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.TimeZone;

@Service
public class ElectionService {


    public Mono<String> create(String name, TimeZone time){

        return null;

    }


}
