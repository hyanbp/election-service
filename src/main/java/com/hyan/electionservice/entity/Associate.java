package com.hyan.electionservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "associates")
public class Associate {

    @Id
    private String taxId;

    public Associate(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
