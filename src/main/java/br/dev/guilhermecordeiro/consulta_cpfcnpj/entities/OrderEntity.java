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
@Document(collection = "order")
public class OrderEntity {
    @Id
    private String id;

    private String status;

    private String federalIdRequest;

    private BigDecimal amount;

    @DocumentReference(lazy = true)
    private PersonEntity person;

    @CreatedDate
    private LocalDateTime createdAt;
}
