package br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface WebClientInterface {
    Mono<Object> get(String baseUrl, String path, String params, Map<String, String> headers);

    WebClient.ResponseSpec getRetrieve(String baseUrl, String path, String params);

    Mono<Object> post(String baseUrl, String path, Map<String, String> headers, Object body);
}
