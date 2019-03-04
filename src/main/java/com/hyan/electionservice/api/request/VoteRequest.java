package com.hyan.electionservice.api.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class VoteRequest {

    @ApiModelProperty("Documento de identificação do associado.")
    @NotNull(message = "Parâmetros obrigatórios não informados.")
    String associateCode;

    @ApiModelProperty("Escolha da decisão do voto (SIM ou NAO).")
    @NotNull(message = "Parâmetros obrigatórios não informados.")
    String decisionType;


    public VoteRequest (String associateCode, String decisionType){
        this.associateCode = associateCode;
        this.decisionType = decisionType;
    }
    public VoteRequest(){}

    public String getAssociateCode() {
        return associateCode;
    }

    public void setAssociateCode(String associateCode) {
        this.associateCode = associateCode;
    }

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }
}
