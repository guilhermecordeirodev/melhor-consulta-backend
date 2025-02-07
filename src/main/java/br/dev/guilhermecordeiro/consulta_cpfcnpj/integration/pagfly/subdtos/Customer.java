package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String birthdate;
    private LocalDateTime createdAt;
    private LocalDateTime externalRef;
    private Document document;
    private Address address;
}
