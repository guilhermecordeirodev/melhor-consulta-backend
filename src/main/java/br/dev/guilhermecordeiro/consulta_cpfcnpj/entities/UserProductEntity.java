package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_product")
public class UserProductEntity {
    @Id
    private String id;
    private String productId;
    private String userId;
    private int quantityPurchased;
    private int quantityUsed;
    private int remainingQuantity;
}
