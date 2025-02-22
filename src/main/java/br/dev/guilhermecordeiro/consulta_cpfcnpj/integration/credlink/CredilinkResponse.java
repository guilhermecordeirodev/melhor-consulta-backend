package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos.CredilinkWebService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CredilinkResponse {
    @JsonProperty("code_message")
    private String codeMessage;
    private String code;
    private String service;
    @JsonProperty("credilink_webservice")
    private CredilinkWebService credilinkWebservice;
}



