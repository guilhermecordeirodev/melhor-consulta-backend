package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Garante que apenas campos não nulos são serializados
public class Document {

    @JsonProperty("type") // Força a serialização do campo com este nome
    private String type;

    @JsonProperty("number") // Força a serialização do campo com este nome
    private String number;

}