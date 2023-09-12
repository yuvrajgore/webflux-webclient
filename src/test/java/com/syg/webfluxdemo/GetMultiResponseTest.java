package com.syg.webfluxdemo;

import com.syg.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetMultiResponseTest extends BaseTests{

    @Autowired
    private WebClient webClient;

    @Test
    public void stepVerifierFluxTest(){

        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("reactiveMath/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void stepVerifierFluxStreamTest(){

        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("reactiveMath/table/{input}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
