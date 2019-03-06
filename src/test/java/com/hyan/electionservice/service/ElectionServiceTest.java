package com.hyan.electionservice.service;

import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.entity.DecisionType;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ElectionServiceTest {

    public static final String ELECTION_CODE = "123";
    public static final String TAX_ID_TESTE = "00001000010001";
    @InjectMocks
    private ElectionService electionService;

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private AssociateService associateService;

    @Mock
    private HistoryElectionRepository historyElectionRepository;


    @Test
    public void createElectionShouldElectiontShouldRetunSuccess() {
        Election election = ElectionStub.create();
        when(electionRepository.save(any())).thenReturn(Mono.just(election));

        String expected = electionService.create("teste", 1).block();

        Assert.assertEquals(expected, election.getId());

    }

    @Test
    public void getResultElectionShouldElectiontReturnSuccess() {
        Election election = ElectionStub.create();
        when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(election));

        Election expected = electionService.resultVote("teste").block();

        Assert.assertEquals(expected.getName(), election.getName());
        Assert.assertEquals(expected.getYes(), election.getYes());
        Assert.assertEquals(expected.getNo(), election.getNo());

    }

    @Test(expected = ResponseStatusException.class)
    public void getResultElectionShouldNotFoundElection() {
        when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());

        Election expected = electionService.resultVote("teste").block();
        Assert.assertNull(expected);
    }


    @Test
    public void getElectionShouldReturnSuccess() {
        Election expected = ElectionStub.create();
        when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(expected));

        Election election = electionService.getElection("teste").block();

        Assert.assertNotNull(election);
        Assert.assertEquals(expected.getName(), expected.getName());
        Assert.assertEquals(expected.getId(), expected.getId());

    }

    @Test(expected = ResponseStatusException.class)
    public void getElectionHavingNotFoundElctionbyId() {
        when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());
        electionService.getElection("teste").block();
    }

    @Test(expected = ResponseStatusException.class)
    public void getElectionShouldHavingClosedSession() {
        Election expected = ElectionStub.create();
        expected.setOpenElection(LocalDateTime.now().minusMinutes(11L));
        expected.setExpirationMinutes(10);

        when(electionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(expected));

        electionService.getElection("teste").block();

    }

    @Test(expected = ResponseStatusException.class)
    public void getElectionHistoryHasAssociateAlreadyVotedSession() {
        when(historyElectionRepository.findByTaxIdAndElectionCode(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(new HistoryElection()));
        Assert.assertTrue(electionService.getHistoryElection("teste", "testeCode").block());

    }

    @Test
    public void getElectionHistoryHasAssociateNotVotedSession() {
        when(historyElectionRepository.findByTaxIdAndElectionCode(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.empty());
        Assert.assertFalse(electionService.getHistoryElection("teste", "testeCode").block());

    }

    @Test
    public void executeVoteSucces(){
        Election election = ElectionStub.create();
        when(electionRepository.findById(ELECTION_CODE)).thenReturn(Mono.just(election));
        when(electionService.getElection(ELECTION_CODE)).thenReturn(Mono.just(election));
        when(associateService.getAssociate(TAX_ID_TESTE)).thenReturn(Mono.just(new Associate(TAX_ID_TESTE)));
        when(historyElectionRepository.findByTaxIdAndElectionCode(TAX_ID_TESTE, ELECTION_CODE)).thenReturn(Mono.empty());
        when(electionService.getHistoryElection(TAX_ID_TESTE,ELECTION_CODE)).thenReturn(Mono.empty());
        when(electionRepository.save(any())).thenReturn(Mono.empty());
        when(historyElectionRepository.save(any())).thenReturn(Mono.empty());


        electionService.vote(ELECTION_CODE, DecisionType.NAO.name(),TAX_ID_TESTE).block();

        Mockito.verify(electionRepository, times(1)).save(any(Election.class));
        Mockito.verify(historyElectionRepository, times(1)).save(any(HistoryElection.class));


    }

}
