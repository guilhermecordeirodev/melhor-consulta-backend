package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

@Data
public class ChequesPreDatados {
    private Cheque cheque;
    private int quantidade;
}
