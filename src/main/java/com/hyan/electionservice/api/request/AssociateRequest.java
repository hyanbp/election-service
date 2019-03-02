package com.hyan.electionservice.api.request;

import javax.validation.constraints.NotEmpty;

public class AssociateRequest {

    @NotEmpty
    private String taxId;


    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

}
