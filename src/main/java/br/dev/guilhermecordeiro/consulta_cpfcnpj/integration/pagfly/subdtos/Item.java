package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String title;
    private int quantity;
    private boolean tangible;
    private long unitPrice;
    private String externalRef;
}