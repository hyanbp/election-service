package com.hyan.electionservice.service;

import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.repository.ElectionRepository;
import com.hyan.electionservice.stub.ElectionStub;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class ElectionServiceTest {

    @InjectMocks
    private ElectionService electionService;

    @Mock
    private ElectionRepository electionRepository;


    @Test
    public void createElectionShouldElectiontWithIncludeParameter(){
        Election election = ElectionStub.create();
        Mockito.when(electionRepository.save(Mockito.any())).thenReturn(Mono.just(election));

        String expected = electionService.create("teste", 1).block();

        Assert.assertEquals(expected, election.getId());

    }

    @Test
    public void getResultElectionShouldElectiontWithIncludeParameter(){
        Election election = ElectionStub.create();
        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(election));

        Election expected = electionService.resultVote("teste").block();

        Assert.assertEquals(expected.getName(), election.getName());
        Assert.assertEquals(expected.getYes(), election.getYes());
        Assert.assertEquals(expected.getNo(), election.getNo());

    }

    @Test(expected = ResponseStatusException.class)
    public void getResultElectionShouldNotFoundElection(){
        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());

        Election expected = electionService.resultVote("teste").block();
        Assert.assertNull(expected);
    }


}
