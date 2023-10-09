package com.syg.webfluxdemo.webtestclient;

import com.syg.webfluxdemo.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class SimpleWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void stepVerifierTest(){

        Flux<Response> response = this.webTestClient
                .get()
                .uri("/reactiveMath/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();

        StepVerifier.create(response)
                .expectNextMatches(response1 -> response1.getOutput() == 25)
                .verifyComplete();
    }

    @Test
    public void fluentAssertionTest(){

        this.webTestClient
                .get()
                .uri("/reactiveMath/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));
    }
}
