package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.UserEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.UserProductEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.UserProductRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProductRepository userProductRepository;

    public Mono<UserEntity> findById(String id) {

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuário não encontrado: " + id)))
                .flatMap(user ->
                        userProductRepository.findByUserId(user.getId())
                                .defaultIfEmpty(Collections.emptyList())
                                .map(userProducts -> {
                                    int total = userProducts.stream()
                                            .mapToInt(UserProductEntity::getRemainingQuantity)
                                            .sum();
                                    user.setQuantityOfSearch(user.getQuantityOfSearch() + total);
                                    return user;
                                })
                );
    }

    public Mono<UserEntity> findOrCreateUser(Map<String, Object> googleBody) {
        if (googleBody == null) {
            return Mono.error(new AuthenticationCredentialsNotFoundException("Token inválido (Map nulo)"));
        }

        // Extrai campos do Map
        String sub = (String) googleBody.get("sub");
        String email = (String) googleBody.get("email");
        String name = (String) googleBody.get("name");
        String picture = (String) googleBody.get("picture");

        // Se sub estiver null, não há como associar ao Google
        if (sub == null) {
            return Mono.error(new RuntimeException("Campo 'sub' não encontrado no token Google: " + googleBody));
        }

        return userRepository.findByGoogleSub(sub)
                // Se não encontrar, cria novo
                .switchIfEmpty(Mono.defer(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setGoogleSub(sub);
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setPicture(picture);
                    newUser.setQuantityOfSearch(0);
                    // Setar outros campos default se houver...
                    return userRepository.save(newUser);
                }))
                // publishOn é opcional; se precisar rodar algo bloqueante, use boundedElastic
                // .publishOn(Schedulers.boundedElastic())
                .flatMap(user -> {
                    boolean updated = false;

                    // Verifica se algo mudou e atualiza
                    if (email != null && !email.equals(user.getEmail())) {
                        user.setEmail(email);
                        updated = true;
                    }
                    if (name != null && !name.equals(user.getName())) {
                        user.setName(name);
                        updated = true;
                    }
                    if (picture != null && !picture.equals(user.getPicture())) {
                        user.setPicture(picture);
                        updated = true;
                    }

                    // Só salva se houve alteração
                    if (updated) {
                        return userRepository.save(user);
                    } else {
                        return Mono.just(user);
                    }
                });
    }

}
