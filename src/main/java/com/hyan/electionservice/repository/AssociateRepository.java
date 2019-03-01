package com.hyan.electionservice.repository;

import com.hyan.electionservice.entity.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AssociateRepository extends ReactiveMongoRepository<Associate, String> {
}
