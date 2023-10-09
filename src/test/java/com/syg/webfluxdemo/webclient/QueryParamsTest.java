package com.syg.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class QueryParamsTest extends BaseTests{
    @Autowired
    private WebClient webClient;

    private String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest(){
        URI uri = UriComponentsBuilder.fromUriString(queryString).build(10, 20);

        Flux<Integer> intFlux = this.webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(intFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void queryParamsWithUriBuilderTest(){
        Map<String, Integer> map = Map.of(
                "count", 10, "page", 20
        );
        Flux<Integer> intFlux = this.webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(intFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
