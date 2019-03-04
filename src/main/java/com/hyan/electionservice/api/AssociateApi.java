package com.hyan.electionservice.api;

import com.hyan.electionservice.api.request.AssociateRequest;
import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.api.response.AssociateResponse;
import com.hyan.electionservice.api.response.ElectionResponse;
import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.service.AssociateService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/v1/associates")
@Validated
public class AssociateApi {

    public static final String BAD_REQUEST_MESSAGE = "Parâmetros obrigatórios não informados.";
    private Logger logger = LoggerFactory.getLogger(ElectionApi.class);
    private AssociateService associateService;

    public AssociateApi(AssociateService associateService) {
        this.associateService = associateService;

    }


    @PostMapping
    @ApiOperation(value = "Cria um associado.")
    public Mono<AssociateResponse> postElection(AssociateRequest request) {

        logger.info("Criando um Associado. CPF [{}]", request.getTaxId());

        if(StringUtils.isBlank(request.getTaxId())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
        }
        return associateService.create(request.getTaxId())
                .map(x -> new AssociateResponse(x.getTaxId()))
                .doOnSuccess(y -> logger.info("Criação de Associado realizado com sucesso. CPF [{}]", y.getTaxId()));
    }


}
