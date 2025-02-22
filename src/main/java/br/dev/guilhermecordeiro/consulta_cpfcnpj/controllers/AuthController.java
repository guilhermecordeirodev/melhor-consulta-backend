package br.dev.guilhermecordeiro.consulta_cpfcnpj.controllers;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.GoogleTokenRequest;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.LoginResponse;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.UserEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.GoogleService;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.JwtService;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GoogleService googleService;

    @PostMapping("/auth/google")
    public Mono<LoginResponse> loginWithGoogle(@RequestBody GoogleTokenRequest request) {
        return googleService.validateIdToken(request.getIdToken())
                .map(user -> {
                    String jwt = jwtService.generateToken(user);
                    return new LoginResponse(jwt, user);
                });
    }

    @GetMapping("/credits")
    public Mono<UserEntity> getCurrentUser(@AuthenticationPrincipal JwtAuthenticationToken jwt) {
        return userService.findById(jwt.getToken().getSubject());
    }
}
