package com.hyan.electionservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.service.ElectionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/elections")
@Validated
public class ElectionApi {

    private ElectionService electionService;
    private ObjectMapper objectMapper;

    public ElectionApi(ElectionService electionService, ObjectMapper objectMapper) {
        this.electionService = electionService;
        this.objectMapper = objectMapper;
    }


    @PostMapping
    @ApiOperation(value = "Cria sessão/pauta de votação")
    public Mono<String> postElection(ElectionRequest request) {

        return electionService.create(request.getName(), request.getExpirationToMinutes());
    }

    @PostMapping("/{election}/vote")
    @ApiOperation(value = "Votação")
    public Mono<Void> putVote(@PathVariable String election,
                                @RequestParam @ApiParam("Escolha do voto (SIM, NAO)") DecisionType decisionType,
                                @RequestParam String  associate) {



        return electionService.voting(election,decisionType.name(),associate);
    }


}
