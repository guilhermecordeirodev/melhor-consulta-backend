package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String birthdate;
    private String createdAt;
    private String externalRef;
    private String document;
    private Address address;
}
