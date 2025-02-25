package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserEntity {
    @Id
    private String id;
    private String googleSub;
    private String email;
    private String name;
    private String picture;
    private int quantityOfSearch;
}
