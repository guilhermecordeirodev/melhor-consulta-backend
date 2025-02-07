package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Data;

@Data
public class Fee {
    private long netAmount;
    private long estimatedFee;
    private long fixedAmount;
    private long spreadPercent;

}