package com.hyan.electionservice.client.response;

public class ValidateTaxIdResponse {

    private String status;

    public ValidateTaxIdResponse (){}

    public ValidateTaxIdResponse(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
