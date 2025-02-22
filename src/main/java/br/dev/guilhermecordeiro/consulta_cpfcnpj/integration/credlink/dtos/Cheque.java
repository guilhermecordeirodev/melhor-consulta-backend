package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

@Data
public class Cheque {
    private String cliente;
    private String valor;
    private String banco;
    private String emissao;
    private String vencimento;
    private String numerocheque;
}
