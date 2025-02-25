package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String birthdate;
    private String createdAt;
    private String externalRef;
    private Document document;
    private Address address;
}
