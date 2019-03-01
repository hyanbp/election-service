package com.hyan.electionservice.repository;

import com.hyan.electionservice.entity.Election;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends ReactiveMongoRepository<Election, String> {
}
