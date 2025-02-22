package br.dev.guilhermecordeiro.consulta_cpfcnpj.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestContext {
    private String federalId;
    private String productId;
    private String userId;
}
