package com.syg.webfluxdemo.config;

import com.syg.webfluxdemo.dto.InputFailedValidationResponse;
import com.syg.webfluxdemo.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;
    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction1() {
        return RouterFunctions.route()
                .GET("router1/square/{input}", requestHandler::squareHandler)
                .GET("router1/table/{input}", requestHandler::tableHandler)
                .GET("router1/table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("router1/multiply", requestHandler::multiplyHandler)
                .GET("router1/square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/table/{input}", requestHandler::tableHandler)
                .GET("router/table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("router/multiply", requestHandler::multiplyHandler)
                .GET("router/square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (error, request) -> {
            InputValidationException exception = (InputValidationException) error;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setMessage(exception.getMessage());
            response.setErrorCode(exception.getErrorCode());
            response.setInput(exception.getInput());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
