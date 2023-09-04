package com.syg.webfluxdemo.controller;

import com.syg.webfluxdemo.dto.MultiplayRequest;
import com.syg.webfluxdemo.dto.Response;
import com.syg.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reactiveMath")
public class ReactiveMathController {

    @Autowired
    private ReactiveMathService mathService;


    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return this.mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplayRequest> multiplayRequestMono,
                                   @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return this.mathService.multiply(multiplayRequestMono);
    }
}
