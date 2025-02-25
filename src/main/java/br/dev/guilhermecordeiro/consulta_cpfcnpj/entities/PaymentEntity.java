package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@Document(collection = "payment")
public class PaymentEntity {
    @Id
    private String id;

    private String paymentMethod;

    private String status;

    private BigDecimal amount;

    private String qrCode;

    private String transactionId;

    private String orderId;

}
