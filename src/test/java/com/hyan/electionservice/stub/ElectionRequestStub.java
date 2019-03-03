package com.hyan.electionservice.stub;

import com.hyan.electionservice.api.request.ElectionRequest;

public class ElectionRequestStub {

    public static ElectionRequest create(){
        return new ElectionRequest("TESTE",1);
    }

}
