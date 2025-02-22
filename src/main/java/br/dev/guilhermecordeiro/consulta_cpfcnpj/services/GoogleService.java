package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces.WebClientInterface;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Service
public class GoogleService {

    @Autowired
    private WebClientInterface webClient;
    @Autowired
    private UserService userService;

    public Mono<UserEntity> validateIdToken(String idToken) {
        return webClient.getRetrieve("https://oauth2.googleapis.com", "/tokeninfo", "?id_token=".concat(idToken))
                .bodyToMono(Map.class)
                .flatMap(body -> {
                    if (body.containsKey("error") || body.containsKey("error_description")) {
                        return Mono.error(new RuntimeException("Token Google inválido: " + body));
                    }

                    return userService.findOrCreateUser(body);
                });
    }
}

