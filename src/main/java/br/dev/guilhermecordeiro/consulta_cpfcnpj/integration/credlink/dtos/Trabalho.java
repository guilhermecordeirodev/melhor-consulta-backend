package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Trabalho {
    private String dataadmissao;
    private String pis;
    private BigDecimal rendaestimada;
}
