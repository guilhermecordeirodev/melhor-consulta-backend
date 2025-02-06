package br.dev.guilhermecordeiro.consulta_cpfcnpj.controllers;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.FlowProcessing;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.OrderRequestDTO;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FlowController {

    private final FlowProcessing flowProcessing;

    @PostMapping("/generate-payment")
    public Mono<Object> generatePayment(@RequestBody RequestContext requestContext) {
        return flowProcessing.generatePayment(requestContext);
    }

    @GetMapping("/search/{orderId}")
    public Mono<Object> searchData(@PathVariable String orderId) {
        return flowProcessing.searchData(orderId);
    }

}