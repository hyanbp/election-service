package com.hyan.electionservice.api.response;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class AssociateResponse {

    private String taxId;
    private boolean alreadyVoted;

    public AssociateResponse(String taxId, boolean alreadyVoted) {
        this.taxId = taxId;
        this.alreadyVoted =alreadyVoted;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public boolean isAlreadyVoted() {
        return alreadyVoted;
    }

    public void setAlreadyVoted(boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }
}
