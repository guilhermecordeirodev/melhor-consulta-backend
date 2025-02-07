package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {
    private String type;
    private String number;
}