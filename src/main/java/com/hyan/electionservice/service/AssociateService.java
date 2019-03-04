package com.hyan.electionservice.service;

import com.hyan.electionservice.client.ValidateTaxIdClient;
import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.repository.AssociateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.hyan.electionservice.entity.EnableVoteType.ABLE_TO_VOTE;

@Service
public class AssociateService {

    private AssociateRepository associateRepository;

    private ValidateTaxIdClient validateTaxIdClient;

    public static final String TAXID_NOT_ENABLED_VOTED_MESSAGE = "CPF não habilitado a votar.";
    public static final String ASSOCIATE_NOT_FOUND_MESSAGE = "Associado não encontrado.";

    public AssociateService(AssociateRepository associateRepository, ValidateTaxIdClient validateTaxIdClient){
        this.associateRepository = associateRepository;
        this.validateTaxIdClient = validateTaxIdClient;
    }

    public Mono<Associate> create(String taxId){
        return associateRepository.save(new Associate(taxId));
    }

    public Mono<Associate> getAssociate(String associateCode) {
        return validateTaxIdClient.validateTaxId(associateCode)
                .filter(x -> ABLE_TO_VOTE.name().equals(x.getStatus()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, TAXID_NOT_ENABLED_VOTED_MESSAGE)))
                .flatMap(a -> associateRepository.findById(associateCode))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ASSOCIATE_NOT_FOUND_MESSAGE)));
    }

}
