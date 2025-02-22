package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

@Data
public class Endereco {
    private String uf;
    private String cidade;
    private String complemento;
    private String endereco;
    private int numero;
    private int contador;
    private String bairro;
    private String cep;
}
