package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.enums.TypeParameterEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "parameters")
public class ParametersEntity {
    @Id
    private String id;
    private TypeParameterEnum type;
    private String name;
    private String value;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
