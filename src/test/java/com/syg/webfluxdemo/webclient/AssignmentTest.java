package com.syg.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AssignmentTest extends BaseTests{

    private static final String format = "%d %s %d = %s";
    private static final int A = 10;
    @Autowired
    private WebClient webClient;

    @Test
    public void test() {
        Flux<String> stringflux = Flux.range(1, 5)
                .flatMap(b -> Flux.just("+", "-", "*", "/")
                        .flatMap(op -> send(b, op)))
                .doOnNext(System.out::println);

        StepVerifier.create(stringflux)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int b, String op){
       return this.webClient
                .get()
                .uri("calculator/{a}/{b}", A, b)
                .headers(h -> h.set("OP", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(v-> String.format(format, A, op, b, v));

    }
}
