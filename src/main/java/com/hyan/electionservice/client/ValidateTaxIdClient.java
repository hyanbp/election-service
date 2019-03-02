package com.hyan.electionservice.client;

import com.hyan.electionservice.client.response.ValidateTaxIdResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ValidateTaxIdClient {

    @Value("https://user-info.herokuapp.com/users/")
    private String url;


    public Mono<ValidateTaxIdResponse> validateTaxId(String taxId){
        return WebClient.create().get()
                .uri(url + taxId)
                .retrieve()
                .bodyToMono(ValidateTaxIdResponse.class);
    }

}