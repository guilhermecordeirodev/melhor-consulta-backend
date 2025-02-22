package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DadosSociedades {
    private List<Sociedade> sociedades;
}
