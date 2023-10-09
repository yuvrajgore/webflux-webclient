package com.syg.webfluxdemo.webclient;

import com.syg.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetSingleResponseTest extends BaseTests{

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){

        Response response = this.webClient
                .get()
                .uri("reactiveMath/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);
    }

    @Test
    public void stepVerifierTest(){

        Mono<Response> response = this.webClient
                .get()
                .uri("reactiveMath/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                        .expectNextMatches(response1 -> response1.getOutput() == 25)
                                .verifyComplete();
    }
}
