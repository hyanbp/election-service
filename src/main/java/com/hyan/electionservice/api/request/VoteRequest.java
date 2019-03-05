package com.hyan.electionservice.api.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class VoteRequest {

    @ApiModelProperty("CPF do associado.")
    @NotNull(message = "Parâmetros obrigatórios não informados.")
    String taxIdAssociate;

    @ApiModelProperty("Escolha da decisão do voto (SIM ou NAO).")
    @NotNull(message = "Parâmetros obrigatórios não informados.")
    String decision;


    public VoteRequest (String taxIdAssociate, String decisionType){
        this.taxIdAssociate = taxIdAssociate;
        this.decision = decisionType;
    }
    public VoteRequest(){}

    public String getTaxIdAssociate() {
        return taxIdAssociate;
    }

    public void setTaxIdAssociate(String taxIdAssociate) {
        this.taxIdAssociate = taxIdAssociate;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
