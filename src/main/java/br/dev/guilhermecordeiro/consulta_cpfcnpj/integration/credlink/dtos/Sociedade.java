package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sociedade {
    private String empresasocio;
    @JsonProperty("desc_cargo_sociedade")
    private String desccargosociedade;
    private String entradasociedade;
    private String cnpjempresasocio;
    private String enderufempresasocio;
    private String endercidadeempresasocio;
    private String enderempresasocio;
    private int endernumempresasocio;
    private String enderbairroempresasocio;
    private String endercepempresasocio;
}
