package br.dev.guilhermecordeiro.consulta_cpfcnpj.dto;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private UserEntity user;
}