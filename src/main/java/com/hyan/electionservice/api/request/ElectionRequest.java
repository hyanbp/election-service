package com.hyan.electionservice.api.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.TimeZone;

public class ElectionRequest {

    @NotEmpty(message = "Nome da pauta/Eleição obrigatório.")
    @ApiModelProperty("Nome da pauta/eleição.")
    private String name;
    @ApiModelProperty("Tempo de duração da pauta.")
    private Integer expirationToMinutes;

    public ElectionRequest(String name, Integer expirationToMinutes){
        this.name = name;
        this.expirationToMinutes = expirationToMinutes;
    }

    public ElectionRequest(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpirationToMinutes() {
        return expirationToMinutes;
    }

    public void setExpirationToMinutes(Integer expirationToMinutes) {
        this.expirationToMinutes = expirationToMinutes;
    }
}
