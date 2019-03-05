package com.hyan.electionservice.api;

import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.api.request.VoteRequest;
import com.hyan.electionservice.api.response.ElectionResponse;
import com.hyan.electionservice.api.response.ElectionResultVoteResponse;
import com.hyan.electionservice.entity.DecisionType;
import com.hyan.electionservice.service.ElectionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/elections")
@Validated
public class ElectionApi {

    public static final String BAD_REQUEST_MESSAGE = "Parâmetros obrigatórios não informados.";
    private ElectionService electionService;

    public ElectionApi(ElectionService electionService) {
        this.electionService = electionService;
    }

    private Logger logger = LoggerFactory.getLogger(ElectionApi.class);


    @PostMapping
    @ApiOperation(value = "Cria uma eleição/pauta de votação")
    public Mono<ElectionResponse> postElection(@RequestBody @Valid ElectionRequest request) {
        logger.info("Criando uma eleição/pauta de votação, Nome:[{}]", request.getName());

        if (StringUtils.isBlank(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
        }
        return electionService.create(request.getName(), request.getExpirationToMinutes())
                .map(x -> new ElectionResponse(x))
                .doOnSuccess(y -> logger.info("Eleição criada com sucesso. Codigo: [{}]", y.getElectionCode()));
    }

    @PostMapping("/{electionCode}/vote")
    @ApiOperation(value = "Votação")
    public Mono<Void> postVote(@PathVariable @ApiParam(value = "Código da Eleição/pauta.", required = true) String electionCode,
                               @RequestBody @Valid VoteRequest request) {
        logger.info("Iniciando votação para a eleição/pauta:[{}] para o Associado:[{}]", electionCode, request.getTaxIdAssociate());

        DecisionType decisionType = DecisionType.get(request.getDecision());
        return electionService.vote(electionCode, decisionType.name(), request.getTaxIdAssociate())
                .doOnSuccess(y -> logger.info("Votação realizada com sucesso para o Associado: [{}]", request.getTaxIdAssociate()));
    }


    @GetMapping("/{electionCode}/result")
    @ApiOperation(value = "Resultado da votação")
    public Mono<ElectionResultVoteResponse> getResultVote(@PathVariable String electionCode) {
        logger.info("Inicando busca pelo resultado da Eleição. Código da eleição [{}]", electionCode);
        return electionService.resultVote(electionCode)
                .map(el -> new ElectionResultVoteResponse(el.getYes(), el.getNo()))
                .doOnSuccess(x -> logger.info("Busca de resultado realizado com sucesso para o Código da eleição [{}]", electionCode));
    }


}
