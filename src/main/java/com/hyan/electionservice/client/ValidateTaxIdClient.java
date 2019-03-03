package com.hyan.electionservice.client;

import com.hyan.electionservice.client.response.ValidateTaxIdResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class ValidateTaxIdClient {

    @Value("https://user-info.herokuapp.com/users/")
    private String url;


    public Mono<ValidateTaxIdResponse> validateTaxId(String taxId) {
        return WebClient.create().get()
                .uri(url + taxId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, x -> Mono.error(new ResponseStatusException( HttpStatus.BAD_REQUEST, "CPF INVALIDO")))
                .bodyToMono(ValidateTaxIdResponse.class);
    }

}