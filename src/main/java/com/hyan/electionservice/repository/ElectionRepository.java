package com.hyan.electionservice.repository;

import com.hyan.electionservice.entity.Election;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ElectionRepository extends ReactiveMongoRepository<Election, String> {

    @Query("{ 'closedSession' : ?0 }")
    Flux<Election> findAllByClosedSession(boolean closedSession);
}
