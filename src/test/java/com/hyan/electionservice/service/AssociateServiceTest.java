package com.hyan.electionservice.service;

import com.hyan.electionservice.client.ValidateTaxIdClient;
import com.hyan.electionservice.client.response.ValidateTaxIdResponse;
import com.hyan.electionservice.entity.Associate;
import com.hyan.electionservice.entity.EnableVoteType;
import com.hyan.electionservice.repository.AssociateRepository;
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
public class AssociateServiceTest {

    @Mock
    private ValidateTaxIdClient validateTaxIdClient;

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateService associateService;


    @Test
    public void getAssociateTestReturnSuccess() {
        Associate expected = new Associate("teste");
        Mockito.when(validateTaxIdClient.validateTaxId(Mockito.anyString())).thenReturn(Mono.just(new ValidateTaxIdResponse(EnableVoteType.ABLE_TO_VOTE.name())));
        Mockito.when(associateRepository.findById(Mockito.anyString())).thenReturn(Mono.just(expected));

        Associate associate = associateService.getAssociate("teste").block();

        Assert.assertNotNull(associate);
        Assert.assertEquals(expected.getTaxId(), associate.getTaxId());

    }


    @Test(expected = ResponseStatusException.class)
    public void getAssociateNotFoundAssociateFindById() {
        Mockito.when(validateTaxIdClient.validateTaxId(Mockito.anyString())).thenReturn(Mono.just(new ValidateTaxIdResponse(EnableVoteType.ABLE_TO_VOTE.name())));
        Mockito.when(associateRepository.findById(Mockito.anyString())).thenReturn(Mono.empty());

        associateService.getAssociate("teste").block();

    }

    @Test(expected = ResponseStatusException.class)
    public void getAssociateTestReturnSuccessHavinAssociateUnableToVote() {
        Mockito.when(validateTaxIdClient.validateTaxId(Mockito.anyString())).thenReturn(Mono.just(new ValidateTaxIdResponse(EnableVoteType.UNABLE_TO_VOTE.name())));
       associateService.getAssociate("teste").block();


    }

    @Test(expected = ResponseStatusException.class)
    public void createAssociateDuplicatedHavingAssociateUnathorized(){
        Mockito.when(associateRepository.findById(Mockito.anyString())).thenReturn(Mono.just(new Associate()));
        Associate associate = associateService.create("teste").block();
        Assert.assertNull(associate);
    }

}
