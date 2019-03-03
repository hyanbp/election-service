package com.hyan.electionservice.api.response;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class AssociateResponse {

    private String taxId;

    public AssociateResponse(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

}
