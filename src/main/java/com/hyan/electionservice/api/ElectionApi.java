package com.hyan.electionservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.api.response.ElectionResponse;
import com.hyan.electionservice.api.response.ResultVoteResponse;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.service.ElectionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(ElectionApi.class);


    @PostMapping
    @ApiOperation(value = "Cria uma eleição/pauta de votação")
    public Mono<ElectionResponse> postElection(ElectionRequest request) {
        logger.info("Criando uma eleição/pauta de votação, Nome:[{}]", request.getName());
        return electionService.create(request.getName(), request.getExpirationToMinutes())
                .map(x -> new ElectionResponse(x))
                .doOnSuccess(y -> logger.info("Eleição criada com sucesso. Codigo: [{}]", y.getElectionCode()));
    }

    @PostMapping("/{electionCode}/vote")
    @ApiOperation(value = "Votação")
    public Mono<Void> postVote(@PathVariable String electionCode,
                                @RequestParam @ApiParam("Escolha do voto (SIM, NAO)") DecisionType decisionType,
                                @RequestParam String  associate) {
        logger.info("Iniciando votação para a eleição/pauta:[{}] para o Associado:[{}]", electionCode, associate);
        return electionService.vote(electionCode,decisionType.name(),associate)
                .doOnSuccess(y ->  logger.info("Votação realizada com sucesso para o Associado: [{}]", associate));
    }


    @GetMapping("/{electionCode}/result")
    @ApiOperation(value = "Resultado da votação")
    public Mono<ResultVoteResponse> getResultVote(@PathVariable String electionCode) {
        logger.info("Inicando busca pelo resultado da Eleição. Código da eleição [{}]",electionCode);
        return electionService.resultVote(electionCode)
                .map(el  ->  new ResultVoteResponse(el.getYes(),el.getNo()))
                .doOnSuccess(x -> logger.info("Busca de resultado realizado com sucesso para o Código da eleição [{}]", electionCode));
    }


}
