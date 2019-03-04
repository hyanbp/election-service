package com.hyan.electionservice.stub;

import com.hyan.electionservice.entity.Election;

import java.time.LocalDateTime;
import java.util.UUID;

public class ElectionStub {
    public static Election create() {
        Election election = new Election();
        election.setYes(0);
        election.setNo(1);
        election.setId(UUID.randomUUID().toString());
        election.setOpenElection(LocalDateTime.now());
        election.setExpirationMinutes(1);
        return election;
    }
}
