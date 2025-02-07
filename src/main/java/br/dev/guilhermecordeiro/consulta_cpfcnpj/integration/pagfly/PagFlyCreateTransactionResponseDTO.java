package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Customer;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Delivery;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Fee;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Item;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Pix;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Shipping;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Split;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PagFlyCreateTransactionResponseDTO {
    private Long id;
    private Long amount;
    private Long paidAmount;
    private Long refundedAmount;
    private String tenantId;
    private Long companyId;
    private int installments;
    private String paymentMethod;
    private String status;
    private String postbackUrl;
    private String metadata;
    private boolean traceable;
    private String secureId;
    private String secureUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime paidAt;
    private LocalDateTime refundedAt;
    private String ip;
    private String externalRef;
    private String authorizationCode;
    private String basePrice;
    private String interestRate;
    private Customer customer;
    private Fee fee;
    private Pix pix;
    private Object boleto;
    private Object card;
    private Shipping shipping;
    private String refusedReason;
    private List<Item> items;
    private List<Split> splits;
    private List<Object> refunds;
    private Delivery delivery;
    private String payer;
}
