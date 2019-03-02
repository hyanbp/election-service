package com.hyan.electionservice.api.response;

public class ElectionResponse {

    private String electionCode;

    public ElectionResponse(String electionCode) {
        this.electionCode = electionCode;
    }

    public ElectionResponse() {
    }

    public String getElectionCode() {
        return electionCode;
    }

    public void setElectionCode(String electionCode) {
        this.electionCode = electionCode;
    }
}
