package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos.Consulta;
import lombok.Data;

import java.util.List;

@Data
public class Passagem {
    private List<Consulta> consulta;
}
