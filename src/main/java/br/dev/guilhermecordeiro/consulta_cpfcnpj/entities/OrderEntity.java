package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.enums.StatusOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "order")
public class OrderEntity {
    @Id
    private String id;

    private StatusOrder status;

    private String federalIdRequest;

    private BigDecimal amount;

    @DocumentReference(lazy = true)
    private PaymentEntity payment;

    private String userId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
