package com.hyan.electionservice.api;

import com.hyan.electionservice.api.request.AssociateRequest;
import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.api.response.AssociateResponse;
import com.hyan.electionservice.api.response.ElectionResponse;
import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.service.AssociateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/associates")
@Validated
public class AssociateApi {


    private AssociateService associateService;

    public AssociateApi(AssociateService associateService) {
        this.associateService = associateService;
    }

    @PostMapping
    @ApiOperation(value = "Cria um associado.")
    public Mono<AssociateResponse> postElection(AssociateRequest request) {

        return associateService.create(request.getTaxId())
                .map(x -> new AssociateResponse(x.getTaxId(), x.isAlreadyVoted()));
    }


}
