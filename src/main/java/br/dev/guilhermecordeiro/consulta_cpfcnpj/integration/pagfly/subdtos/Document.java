package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    public String type;
    public String number;
}