package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly;

import lombok.Data;

@Data
public class PagFlyWebhookDTO {
    private Long id;
    private String type;
    private String objectId;
    private String url;
    private PagFlyWebhookPaymentData data;
}
