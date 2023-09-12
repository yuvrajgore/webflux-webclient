package com.syg.webfluxdemo;

import com.syg.webfluxdemo.dto.MultiplayRequest;
import com.syg.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PostRequestTest extends BaseTests{

    @Autowired
    private WebClient webClient;

    @Test
    public void postTest() {
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactiveMath/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    MultiplayRequest buildRequestDto(int a, int b) {
        MultiplayRequest request = new MultiplayRequest();
        request.setFirstNumber(a);
        request.setSecondNumber(b);
        return request;
    }
}
