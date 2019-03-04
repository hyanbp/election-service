package com.hyan.electionservice.service;

import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.entity.HistoryElection;
import com.hyan.electionservice.repository.ElectionRepository;
import com.hyan.electionservice.repository.HistoryElectionRepository;
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

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class ElectionServiceTest {

    @InjectMocks
    private ElectionService electionService;

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private HistoryElectionRepository historyElectionRepository;


    @Test
    public void createElectionShouldElectiontShouldRetunSuccess() {
        Election election = ElectionStub.create();
        Mockito.when(electionRepository.save(Mockito.any())).thenReturn(Mono.just(election));

        String expected = electionService.create("teste", 1).block();

        Assert.assertEquals(expected, election.getId());

    }

    @Test
    public void getResultElectionShouldElectiontReturnSuccess() {
        Election election = ElectionStub.create();
        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(election));

        Election expected = electionService.resultVote("teste").block();

        Assert.assertEquals(expected.getName(), election.getName());
        Assert.assertEquals(expected.getYes(), election.getYes());
        Assert.assertEquals(expected.getNo(), election.getNo());

    }

    @Test(expected = ResponseStatusException.class)
    public void getResultElectionShouldNotFoundElection() {
        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());

        Election expected = electionService.resultVote("teste").block();
        Assert.assertNull(expected);
    }


    @Test
    public void getElectionShouldReturnSuccess() {
        Election expected = ElectionStub.create();
        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(expected));

        Election election = electionService.getElection("teste").block();

        Assert.assertNotNull(election);
        Assert.assertEquals(expected.getName(), expected.getName());
        Assert.assertEquals(expected.getId(), expected.getId());

    }

    @Test(expected = ResponseStatusException.class)
    public void getElectionHavingNotFoundElctionbyId() {
        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());
        electionService.getElection("teste").block();
    }

    @Test(expected = ResponseStatusException.class)
    public void getElectionShouldHavingClosedSession() {
        Election expected = ElectionStub.create();
        expected.setOpenElection(LocalDateTime.now().minusMinutes(11L));
        expected.setExpirationMinutes(10);

        Mockito.when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(expected));

        electionService.getElection("teste").block();

    }

    @Test(expected = ResponseStatusException.class)
    public void getElectionHistoryHasAssociateAlreadyVotedSession() {
        Mockito.when(historyElectionRepository.findByTaxIdAndElectionCode(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(new HistoryElection()));
        Assert.assertTrue(electionService.getHistoryElection("teste", "testeCode").block());

    }

    @Test
    public void getElectionHistoryHasAssociateNotVotedSession() {
        Mockito.when(historyElectionRepository.findByTaxIdAndElectionCode(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.empty());
        Assert.assertFalse(electionService.getHistoryElection("teste", "testeCode").block());

    }

}
