package br.dev.guilhermecordeiro.consulta_cpfcnpj.config;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces.WebClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class WebClientInterfaceImpl implements WebClientInterface {

    @Autowired
    WebClient webClient;

    @Override
    public Mono<Object> get(String baseUrl, String path, String params, Map<String, String> headers) {
        return webClient.get().uri(baseUrl.concat(path).concat(params))
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.put(key, List.of(value))))
                .retrieve()
                .bodyToMono(Object.class);
    }

    @Override
    public WebClient.ResponseSpec getRetrieve(String baseUrl, String path, String params) {
        return webClient.get().uri(baseUrl.concat(path).concat(params))
                .retrieve();
    }

    @Override
    public Mono<Object> post(String baseUrl, String path, Map<String, String> headers, Object body) {
        return webClient.post().uri(baseUrl + path)
                .headers(httpHeaders ->
                        headers.forEach((key, value) -> httpHeaders.put(key, List.of(value))))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Object.class);
    }
}
