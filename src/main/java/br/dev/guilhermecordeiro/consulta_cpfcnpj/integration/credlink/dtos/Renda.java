package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

@Data
public class Renda {
    private int sequencial;
    private String empresapagadora;
    private String dataadmissao;
    private String valorrenda;
}
