package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import lombok.Data;

import java.util.List;

@Data
public class Alertas {
    private List<Mensagem> alerta;
    private int quantidade;
}
