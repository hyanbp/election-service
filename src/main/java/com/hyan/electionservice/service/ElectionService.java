package com.hyan.electionservice.service;

import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.entity.HistoryElection;
import com.hyan.electionservice.mapper.ElectionMapper;
import com.hyan.electionservice.repository.ElectionRepository;
import com.hyan.electionservice.repository.HistoryElectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ElectionService {

    public static final String CLOSED_SESSION_MESSAGE = "Sessão da Eleição/Pauta de votação ENCERRADA.";
    public static final String ELECTION_NOT_FOUND_MESSAGE = "Eleição/Pauta de votação não encontrada.";
    public static final String ASSOCIETE_ALREADY_VOTED_IN_ELECTION_MESSAGE = "Associado já realizou o voto. Só é permitido um voto por sessão.";


    private ElectionRepository electionRepository;
    private HistoryElectionRepository historyElectionRepository;
    private AssociateService associateService;


    public ElectionService(ElectionRepository electionRepository, AssociateService associateService, HistoryElectionRepository historyElectionRepository) {
        this.electionRepository = electionRepository;
        this.associateService = associateService;
        this.historyElectionRepository = historyElectionRepository;
    }


    public Mono<String> create(String name, Integer expirationToMinutes) {
        return electionRepository.save(ElectionMapper.from(name, expirationToMinutes))
                .map(x -> x.getId());

    }


    public Mono<Void> vote(String electionCode, String decisionType, String taxIdAssociate) {
        return Mono.zip(
                associateService.getAssociate(taxIdAssociate),
                getElection(electionCode),
                getHistoryElection(taxIdAssociate, electionCode))
                .map(o -> {
                    if (DecisionType.SIM.name().equalsIgnoreCase(decisionType)) {
                        o.getT2().addYes();
                    } else {
                        o.getT2().addNo();
                    }
                    electionRepository.save(o.getT2()).subscribe();
                    historyElectionRepository.save(new HistoryElection(o.getT1().getTaxId(), o.getT2().getId())).subscribe();
                    return o;
                }).then();
    }

    public Mono<Election> resultVote(String electionCode) {
        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ELECTION_NOT_FOUND_MESSAGE)));
    }

    public Flux<Election> findSessionCloesed() {
        return electionRepository.findAll()
                .filter(x -> Duration.between(x.getOpenElection(), LocalDateTime.now()).toMinutes() > x.getExpirationMinutes())
                .map(y -> y);
    }


    protected Mono<Election> getElection(String electionCode) {
        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ELECTION_NOT_FOUND_MESSAGE)))
                .filter(x -> Duration.between(x.getOpenElection(), LocalDateTime.now()).toMinutes() <= x.getExpirationMinutes())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, CLOSED_SESSION_MESSAGE)));
    }

    protected Mono<Boolean> getHistoryElection(String taxId, String electionCode) {
        return historyElectionRepository.findByTaxIdAndElectionCode(taxId, electionCode)
                .hasElement()
                .filter(x -> !x)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, ASSOCIETE_ALREADY_VOTED_IN_ELECTION_MESSAGE)));
    }
}
