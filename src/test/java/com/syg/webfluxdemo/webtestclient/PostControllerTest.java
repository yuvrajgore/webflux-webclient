package com.syg.webfluxdemo.webtestclient;

import com.syg.webfluxdemo.controller.ReactiveMathController;
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

@WebFluxTest(ReactiveMathController.class)
public class PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReactiveMathService mathService;

    @Test
    public void postTest(){
        Mockito.when(mathService.multiply(Mockito.any())).thenReturn(Mono.just(new Response(1)));

        this.webTestClient
                .post()
                .uri("/reactiveMath/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("somekey", "someValue"))
                .bodyValue(new MultiplayRequest())
                .exchange()
                .expectStatus().is2xxSuccessful();

    }
}
