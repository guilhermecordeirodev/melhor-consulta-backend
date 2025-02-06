package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.clients;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.FlowProcessing;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces.WebClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public abstract class PagFlyClient extends FlowProcessing {

    @Autowired
    private WebClientInterface webClient;

    @Value("${pag-fly.base-url}")
    private String baseUrl;
    @Value("${pag-fly.private-key}")
    private String privateKey;
    @Value("${pag-fly.password}")
    private String password;

    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic ".concat(Base64.getEncoder()
                .encodeToString((privateKey + ":" + password).getBytes())));

        return headers;
    }

    @Override
    public Mono<Object> generatePaymentMethod(Object o) {

        //montar o payload de request para a pagfly

        return webClient.post(baseUrl, "/transations", getDefaultHeaders(), o);
    }
}
