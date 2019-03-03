package com.hyan.electionservice.service;

import com.hyan.electionservice.client.ValidateTaxIdClient;
import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.mapper.ElectionMapper;
import com.hyan.electionservice.repository.AssociateRepository;
import com.hyan.electionservice.repository.ElectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.hyan.electionservice.entity.EnableVoteType.ABLE_TO_VOTE;

@Service
public class ElectionService {

    public static final String ASSOCIATE_ALREADY_VOTED_MESSAGE = "Associado já realizou o voto. Só é permitido um voto por sessão.";
    public static final String ASSOCIATE_NOT_FOUND_MESSAGE = "Associado não encontrado.";
    public static final String ASSOCIATE_NOT_ENABLED_VOTED_MESSAGE = "Associado não habilitado a votar.";
    public static final String CLOSED_SESSION_MESSAGE = "Sessão da Eleição/Pauta de votação ENCERRADA.";
    public static final String ELECTION_NOT_FOUND_MESSAGE = "Eleição/Pauta de votação não encontrada.";


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


    public Mono<Void> vote(String electionCode, String decisionType, String associateCode) {
        return Mono.zip(
                getAssociate(associateCode), getElection(electionCode))
                .map(y -> {
                    y.getT1().setAlreadyVoted(Boolean.TRUE);
                    associateRepository.save(y.getT1()).subscribe();
                    return y;
                }).map(o -> {
                    if (DecisionType.SIM.name().equalsIgnoreCase(decisionType)) {
                        o.getT2().addYes();
                    } else {
                        o.getT2().addNo();
                    }
                    electionRepository.save(o.getT2()).subscribe();
                    return o;
                }).then();
    }

    public Mono<Election> resultVote(String electionCode) {
        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ELECTION_NOT_FOUND_MESSAGE)));
    }


    private Mono<Election> getElection(String electionCode) {
        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ELECTION_NOT_FOUND_MESSAGE)))
                .filter(x -> Duration.between(x.getOpenElection(), LocalDateTime.now()).toMinutes() <= x.getExpirationMinutes())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, CLOSED_SESSION_MESSAGE)));
    }

    private Mono<Associate> getAssociate(String associateCode) {
        return validateTaxIdClient.validateTaxId(associateCode)
                .filter(x -> ABLE_TO_VOTE.name().equals(x.getStatus()))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, ASSOCIATE_NOT_ENABLED_VOTED_MESSAGE)))
                .flatMap(a -> associateRepository.findById(associateCode))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ASSOCIATE_NOT_FOUND_MESSAGE)))
                .filter(x -> !x.isAlreadyVoted())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, ASSOCIATE_ALREADY_VOTED_MESSAGE)));
    }
}
