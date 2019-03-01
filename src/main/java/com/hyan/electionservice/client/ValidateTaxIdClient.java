package com.hyan.electionservice.client;

import com.hyan.electionservice.client.response.ValidateTaxIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ValidateTaxIdClient {

    @Value("url.validate.taxId")
    private String url;


    @Autowired
    protected WebClient webClient;

    public Mono<ValidateTaxIdResponse> validateTaxId(String taxId){
        return  webClient.get()
                .uri(url + taxId)
                .retrieve()
                .bodyToMono(ValidateTaxIdResponse.class);
    }

}
