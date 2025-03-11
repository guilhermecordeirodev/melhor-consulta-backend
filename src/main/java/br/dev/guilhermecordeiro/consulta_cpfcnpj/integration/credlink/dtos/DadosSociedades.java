package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class DadosSociedades {
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Sociedade> sociedades;
}
