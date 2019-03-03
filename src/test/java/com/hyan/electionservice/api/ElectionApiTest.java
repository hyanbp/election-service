package com.hyan.electionservice.api;

import com.hyan.electionservice.repository.ElectionRepository;
import com.hyan.electionservice.service.ElectionService;
import com.hyan.electionservice.stub.ElectionRequestStub;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

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
    @Ignore
    public void postCreateElectionHavinValidContractAndReturnSuccess() {
        Mockito.when(electionService.create(Mockito.anyString(), Mockito.any(Integer.class))).thenReturn(Mono.just("teste"));

        webTestClient.post()
                .syncBody(ElectionRequestStub.create())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        Mockito.verify(electionRepository).save(Mockito.any());
    }


}
