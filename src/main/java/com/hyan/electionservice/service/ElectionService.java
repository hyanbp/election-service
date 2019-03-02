package com.hyan.electionservice.service;

import com.hyan.electionservice.client.ValidateTaxIdClient;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.entity.EnableVoteType;
import com.hyan.electionservice.excepction.*;
import com.hyan.electionservice.mapper.ElectionMapper;
import com.hyan.electionservice.repository.AssociateRepository;
import com.hyan.electionservice.repository.ElectionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ElectionService {

    private ElectionRepository electionRepository;

    private AssociateRepository associateRepository;

    private ValidateTaxIdClient validateTaxIdClient;


    public ElectionService(ElectionRepository electionRepository, AssociateRepository associateRepository, ValidateTaxIdClient validateTaxIdClient) {
        this.electionRepository = electionRepository;
        this.associateRepository = associateRepository;
        this.validateTaxIdClient = validateTaxIdClient;
    }


    public Mono<String> create(String name, Integer expirationToMinutes) {
        return electionRepository.save(ElectionMapper.from(name, expirationToMinutes))
                .map(x -> x.getId());

    }

    public Mono<Void> vote(String electionCode, String decisionType, String associate) {
        Mono<Election> el = electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new ElectionNotFoundException()))
                .filter(x -> Duration.between(x.getOpenElection(), LocalDateTime.now()).toMinutes() <= x.getExpirationMinutes())
                .switchIfEmpty(Mono.error(new ElectionExpirationSessionException()));


        associateRepository.findById(associate)
                .switchIfEmpty(Mono.error(new AssociateNotFoundExcpetion()))
                .doOnNext(as -> validateTaxId(associate))
                .filter(x -> !x.isAlreadyVoted())
                .switchIfEmpty(Mono.error(new AssociateAlreadyVotedExpcetion()))
                .map(y -> {
                    y.setAlreadyVoted(Boolean.TRUE);
                    associateRepository.save(y).subscribe();
                    return y;
                });

        return el.map(election -> {
            if (DecisionType.SIM.name().equalsIgnoreCase(decisionType)) {
                election.addYes();
            } else {
                election.addNo();
            }
            electionRepository.save(election).subscribe();
            return election;
        }).then();
    }

    public Mono<Election> resultVote(String electionCode) {
        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new ElectionNotFoundException()));
    }


    private void validateTaxId(String taxId) {
        validateTaxIdClient.validateTaxId(taxId)
                .onErrorResume(Exception.class, y -> Mono.error(new Exception()))
                .filter(x -> EnableVoteType.ABLE_TO_VOTE.name().equals(x.getStatus()))
                .switchIfEmpty(Mono.error(new InvalidTaxIdExcption()));

    }

}
