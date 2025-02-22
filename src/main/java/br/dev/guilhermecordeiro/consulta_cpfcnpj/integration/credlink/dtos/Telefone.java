package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Telefone {
    private String whatsapp;
    @JsonProperty("dt_istalacao")
    private String dtIstalacao;
    private String telefone;
    private String cidade;
    private String endereco;
    private String numero;
    private String bairro;
    private String nome;
    private String procon;
    private String cpfcnpj;
    private String cep;
    private String mae;
    private String nasc;
    private String emails;
    private String uf;
    private String complemento;
    private int statustelefone;
    private String atualizacao;
    private int contador;
    private String sexo;
    @JsonProperty("titulo_eleitor")
    private String tituloEleitor;
    private String operadora;
}
