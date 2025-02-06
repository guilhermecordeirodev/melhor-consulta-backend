package br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface WebClientInterface {
    Mono<Object> get(String baseUrl, String path, Map<String, String> headers);
    Mono<Object> post(String baseUrl, String path, Map<String, String> headers, Object body);
}
