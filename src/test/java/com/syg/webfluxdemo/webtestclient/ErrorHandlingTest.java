package com.syg.webfluxdemo.webtestclient;

import com.syg.webfluxdemo.controller.ReactiveMathValidationController;
import com.syg.webfluxdemo.dto.MultiplayRequest;
import com.syg.webfluxdemo.dto.Response;
import com.syg.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class ErrorHandlingTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveMathService mathService;

    @Test
    public void errorHandlingTest(){
        Mockito.when(mathService.multiply(Mockito.any())).thenReturn(Mono.just(new Response(1)));

        this.webTestClient
                .get()
                .uri("/reactiveMath/square/{input}/throw", 5)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("allowed range is 10 - 20")
                .jsonPath("$.errorCode").isEqualTo(100)
                .jsonPath("$.input").isEqualTo(5);

    }
}
