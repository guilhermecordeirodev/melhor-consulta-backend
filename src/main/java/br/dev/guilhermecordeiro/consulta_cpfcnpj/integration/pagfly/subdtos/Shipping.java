package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Data;

@Data
public class Shipping {
    private String fee;
    private Address address;
}