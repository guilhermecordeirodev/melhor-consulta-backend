package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Data;

@Data
public class Delivery {
    private String status;
    private String trackingCode;
    private String createdAt;
    private String updatedAt;
}