package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.enums.StatusOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;

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

    private String productId;
}
