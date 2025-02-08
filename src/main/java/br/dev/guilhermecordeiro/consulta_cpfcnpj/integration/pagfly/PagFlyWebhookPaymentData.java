package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Customer;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Delivery;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Fee;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Item;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Split;
import lombok.Data;

import java.util.List;

@Data
public class PagFlyWebhookPaymentData {
    private Long id;
    private Long amount;
    private Long refundedAmount;
    private Long companyId;
    private Integer installments;
    private String paymentMethod;
    private String status;
    private String postbackUrl;
    private String metadata;
    private boolean traceable;
    private String secureId;
    private String secureUrl;
    private String createdAt;
    private String updatedAt;
    private String paidAt;
    private String ip;
    private String externalRef;
    private String refusedReason;
    private Customer customer;
    private Object card;
    private Object boleto;
    private Object pix;
    private Object shipping;
    private List<Item> items;
    private List<Split> splits;
    private List<Object> refunds;
    private Delivery delivery;
    private Fee fee;
}
