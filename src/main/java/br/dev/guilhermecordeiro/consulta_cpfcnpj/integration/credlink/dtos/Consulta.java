package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Consulta {
    private String datahora;
    @JsonProperty("codigo_credilink")
    private String codigoCredilink;
}
