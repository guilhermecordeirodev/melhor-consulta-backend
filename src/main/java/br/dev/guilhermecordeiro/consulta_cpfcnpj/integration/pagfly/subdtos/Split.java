package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Data;

@Data
public class Split {
    private long amount;
    private long netAmount;
    private long recipientId;
    private boolean chargeProcessingFee;
}
