package com.hyan.electionservice.service;

import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.repository.AssociateRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AssociateService {

    private AssociateRepository associateRepository;

    public AssociateService(AssociateRepository associateRepository){
        this.associateRepository = associateRepository;
    }

    public Mono<Associate> create(String taxId){
        return associateRepository.save(new Associate(taxId));
    }
}
