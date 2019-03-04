package com.hyan.electionservice.api;

import com.hyan.electionservice.api.request.ElectionRequest;
import com.hyan.electionservice.api.request.VoteRequest;
import com.hyan.electionservice.api.response.ElectionResultVoteResponse;
import com.hyan.electionservice.repository.ElectionRepository;
import com.hyan.electionservice.service.ElectionService;
import com.hyan.electionservice.stub.ElectionRequestStub;
import com.hyan.electionservice.stub.ElectionStub;
import com.hyan.electionservice.stub.VoteRequestStub;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ElectionApiTest {

    private WebTestClient webTestClient;

    @Mock
    private ElectionService electionService;

    @Mock
    private ElectionRepository electionRepository;

    @Before
    public void setUp() {
        webTestClient = WebTestClient
                .bindToController(new ElectionApi(electionService))
                .configureClient()
                .baseUrl("/v1/elections")
                .build();
    }

    @Test
    public void postCreateElectionHavinValidContractAndReturnSuccess() {
        Mockito.when(electionService.create(anyString(), any(Integer.class))).thenReturn(Mono.just("teste"));

        webTestClient.post()
                .syncBody(ElectionRequestStub.create())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void postCreateElectionHavingNotFoundParametersContractAndReturnBadRequest() {
        ElectionRequest request = new ElectionRequest();
        webTestClient.post()
                .syncBody(request)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }


    @Test
    public void getResulVoteHavingValidContractReturnSuccess() {
        Mockito.when(electionService.resultVote("102931092831")).thenReturn(Mono.just(ElectionStub.create()));
        webTestClient.get()
                .uri("/102931092831/result")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(ElectionResultVoteResponse.class);
    }

    @Test
    public void getResulVoteHavingInvalidParametersReturnNotFound() {
        webTestClient.get()
                .uri("//result")
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    public void postExecutionVoteHavingValidContractReturnSucess() {
        Mockito.when(electionService.vote(anyString(), anyString(), anyString())).thenReturn(Mono.empty());
        webTestClient.post()
                .uri("/1231231231/vote")
                .syncBody(VoteRequestStub.create())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void postExecutionVoteInvalidContractReturnBadRequest() {
        webTestClient.post()
                .uri("/1231231231/vote")
                .syncBody(new VoteRequest())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


}
