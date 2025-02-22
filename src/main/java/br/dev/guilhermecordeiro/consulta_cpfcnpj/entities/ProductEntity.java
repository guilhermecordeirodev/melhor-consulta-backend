package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "product")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal value;
    private int quantityOfRequests;
    @CreatedDate
    private LocalDateTime createdAt;
}
