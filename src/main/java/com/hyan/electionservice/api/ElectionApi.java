package com.hyan.electionservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyan.electionservice.service.ElectionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/elections")
@Validated
public class ElectionApi {

    private ElectionService electionService;
    private ObjectMapper objectMapper;

    public ElectionApi (ElectionService electionService, ObjectMapper objectMapper) {
        this.electionService = electionService;
        this.objectMapper = objectMapper;
    }


    @PostMapping
    @ApiOperation(value = "Cria sessão/pauta de votação")
    public Mono<String> postElection(ElectionRequest request){

        electionService.create(request.getName(),request.getTime());

        return null;
    }

    @PostMapping
    @ApiOperation(value = "Cria sessão de votação")
    public Mono<String> postElection(){

        return null;
    }


}
