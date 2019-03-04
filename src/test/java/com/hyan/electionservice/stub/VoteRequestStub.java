package com.hyan.electionservice.stub;

import com.hyan.electionservice.api.request.VoteRequest;

public class VoteRequestStub {

    public static VoteRequest create(){
        return new VoteRequest("123123123123","SIM");
    }
}
