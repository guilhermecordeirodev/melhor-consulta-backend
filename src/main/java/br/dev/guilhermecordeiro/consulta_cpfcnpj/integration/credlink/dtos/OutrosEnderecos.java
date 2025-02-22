package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class OutrosEnderecos {
    @JsonProperty("endereco")
    private Endereco endereco;
}