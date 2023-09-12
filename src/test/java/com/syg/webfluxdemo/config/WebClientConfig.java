package com.syg.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                //.defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .filter((clientRequest, exchageFunction) -> tokenGenerator(clientRequest, exchageFunction))
                .build();
    }

 /*   private Mono<ClientResponse> tokenGenerator(ClientRequest cr, ExchangeFunction ef) {
        System.out.println("Generating token");
        ClientRequest clientRequest = ClientRequest.from(cr).headers(httpHeaders -> httpHeaders.setBearerAuth("sample-jwt")).build();
        return ef.exchange(clientRequest);
    }*/

    private Mono<ClientResponse> tokenGenerator(ClientRequest cr, ExchangeFunction ef) {
        ClientRequest clientRequest = cr.attribute("auth")
                .map(v -> v.equals("basic") ? withBasicAuth(cr) : withBearerAuth(cr))
                .orElse(cr);
        return ef.exchange(clientRequest);
    }
    private ClientRequest withBasicAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withBearerAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("sample-jwt"))
                .build();
    }
}
