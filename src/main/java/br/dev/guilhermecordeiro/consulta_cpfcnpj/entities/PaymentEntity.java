package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private LocalDateTime paidAt;

    private String trasactionId;

    @DocumentReference(lazy = true)
    private PersonEntity person;

    @DocumentReference(lazy = true)
    private OrderEntity order;

    @CreatedDate
    private LocalDateTime createdAt;
}
