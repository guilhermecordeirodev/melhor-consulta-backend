package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "federal_identification")
public class FederalIdentificationEntity {
    @Id private String id;
    private String federalId;
    private String name;
    private String email;
    private String phone;
    @CreatedDate private LocalDateTime createdAt;
}
