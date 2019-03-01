package com.hyan.electionservice.service;

import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.entity.Election;
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


    public ElectionService(ElectionRepository electionRepository,AssociateRepository associateRepository) {
        this.electionRepository = electionRepository;
        this.associateRepository =associateRepository;
    }


    public Mono<String> create(String name, Integer expirationToMinutes) {
        return electionRepository.save(ElectionMapper.from(name, expirationToMinutes))
                .map(x -> x.getId());

    }

    public Mono<Void> voting(String electionCode, String decisionType, String associate) {
        associateRepository.findById(associate)
                .switchIfEmpty(Mono.error(new Exception()))
                .filter(x -> !x.isAlreadyVoted())
                .switchIfEmpty(Mono.error(new Exception())).
                map(y -> {
                    y.setAlreadyVoted(Boolean.TRUE);
                    associateRepository.save(y).subscribe();
                    return y;
                });

        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new Exception()))
                .filter(x -> Duration.between(x.getOpenElection(), LocalDateTime.now()).toMinutes() <= x.getExpirationMinutes())
                .switchIfEmpty(Mono.error(new Exception()))

                .map(election -> {
                    if (DecisionType.SIM.name().equalsIgnoreCase(decisionType)) {
                        election.addYes();
                    } else {
                        election.addNo();
                    }
                    electionRepository.save(election).subscribe();
                    return election;
                }).then();
    }

    public Mono<Election> resultVote(String electionCode){
        return electionRepository.findById(electionCode)
                .switchIfEmpty(Mono.error(new Exception()));
    }


}
