package com.hyan.electionservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.api.response.ResultVoteResponse;
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
    @ApiOperation(value = "Cria uma pauta de votação")
    public Mono<String> postElection(ElectionRequest request) {

        return electionService.create(request.getName(), request.getExpirationToMinutes());
    }

    @PostMapping("/{electionCode}/vote")
    @ApiOperation(value = "Votação")
    public Mono<Void> postVote(@PathVariable String electionCode,
                                @RequestParam @ApiParam("Escolha do voto (SIM, NAO)") DecisionType decisionType,
                                @RequestParam String  associate) {

        return electionService.vote(electionCode,decisionType.name(),associate);
    }


    @GetMapping("/{electionCode}/result")
    @ApiOperation(value = "Resultado da votação")
    public Mono<ResultVoteResponse> getResultVote(@PathVariable String electionCode) {
        return electionService.resultVote(electionCode)
                .map(el  ->  new ResultVoteResponse(el.getYes(),el.getNo()));
    }


}
