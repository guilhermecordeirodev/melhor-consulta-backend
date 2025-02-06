package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "person")
public class PersonEntity {
    @Id private String id;
    private String federalId;
    private String name;
    private String email;
}
