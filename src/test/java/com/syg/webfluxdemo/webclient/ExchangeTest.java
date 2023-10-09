package com.syg.webfluxdemo.webclient;

import com.syg.webfluxdemo.dto.InputFailedValidationResponse;
import com.syg.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ExchangeTest extends BaseTests {
    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest() {

        Mono<Object> responseMono = this.webClient
                .get()
                .uri("reactiveMath/square/{input}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(error -> System.out.println(error.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse cr) {
        if(cr.statusCode().value() == 400)
            return cr.bodyToMono(InputFailedValidationResponse.class);
        else
            return cr.bodyToMono(Response.class);
    }
}
