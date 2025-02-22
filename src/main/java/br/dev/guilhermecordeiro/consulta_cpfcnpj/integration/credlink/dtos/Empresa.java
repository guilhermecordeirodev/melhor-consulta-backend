package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

@Data
public class Empresa {
    private String razaosocial;
    private String uf;
    private String cidade;
    private String complemento;
    private String endereco;
    private int numero;
    private int contador;
    private String bairro;
    private String cnpj;
    private String cep;
}
