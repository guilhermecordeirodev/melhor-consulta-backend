package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CredilinkWebService {
    private Map<String, Object> moradores;
    @JsonProperty("enderecos_comerciais")
    private EnderecosComerciais enderecosComerciais;
    @JsonProperty("consulta_bancos")
    private ConsultaBancos consultaBancos;
    private Comercial comercial;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Renda> rendapf;
    private Map<String, Object> parentes;
    @JsonProperty("consulta_ccf619")
    private ConsultaCCF consultaCcf619;
    private Map<String, Object> outrosContatos;
    @JsonProperty("consulta_telefone_proprietario")
    private ConsultaTelefone consultaTelefoneProprietario;
    private Map<String, Object> iptu;
    private String escolaridade;
    @JsonProperty("dados_empresa")
    private String dadosEmpresa;
    @JsonProperty("consultas_realizadas")
    private ConsultasRealizadas consultasRealizadas;
    private Passagem passagem;
    @JsonProperty("consulta_emails_proprietario")
    private ConsultaEmails consultaEmailsProprietario;
    @JsonProperty("pessoa_politicamente_exposta")
    private String pessoaPoliticamenteExposta;
    private Map<String, Object> vizinhos;
    private int rendapresumida;
    @JsonProperty("dataRegistroCredilink")
    private String dataRegistroCredilink;
    @JsonProperty("consulta_telefone_referencia")
    private Map<String, Object> consultaTelefoneReferencia;
    @JsonProperty("outros_enderecos")
    private OutrosEnderecos outrosEnderecos;
    private Map<String, Object> veiculos;
    @JsonProperty("dados_sociedades")
    private DadosSociedades dadosSociedades;
    private Trabalho trabalho;
    private Map<String, Object> obito;
}
