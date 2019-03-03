package com.hyan.electionservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "historics")
public class HistoryElection {

    @Id
    private String id;
    private String taxId;
    private String electionCode;

    public HistoryElection(String taxId, String electionCode) {
        this.electionCode = electionCode;
        this.taxId = taxId;
    }


    public HistoryElection() {
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getElectionCode() {
        return electionCode;
    }

    public void setElectionCode(String electionCode) {
        this.electionCode = electionCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
