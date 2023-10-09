package com.syg.webfluxdemo.webtestclient;

import com.syg.webfluxdemo.config.RequestHandler;
import com.syg.webfluxdemo.config.RouterConfig;
import com.syg.webfluxdemo.dto.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Classes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class RouterfunctionTest {

    @MockBean
    private RequestHandler requestHandler;
    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext applicationContext;
    @BeforeAll
    public void setClient(){
    this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    public void test(){
        Mockito.when(requestHandler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new Response(225)));
        this.webTestClient
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
    }
}
