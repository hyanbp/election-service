package com.hyan.electionservice.repository;

import com.hyan.electionservice.entity.HistoryElection;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HistoryElectionRepository extends ReactiveMongoRepository<HistoryElection, String> {

    @Query("{ 'taxId': ?0, 'electionCode': ?1}")
    Mono<HistoryElection> findByTaxIdAndElectionCode(String taxId, String electionCode);
}
