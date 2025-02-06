package br.dev.guilhermecordeiro.consulta_cpfcnpj.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDTO {
    private String name;
    private String federalId;
    private String email;
    private String federalIdRequest;
    private BigDecimal amount;
}
