package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_product_order")
public class UserProductOrderEntity {
    @Id
    private String id;
    private String federalIdRequested;
    private String userProductId;
}
