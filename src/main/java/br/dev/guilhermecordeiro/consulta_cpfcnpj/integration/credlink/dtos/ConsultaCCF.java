package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConsultaCCF {
    @JsonProperty("cheques_pre_datados")
    private ChequesPreDatados chequesPreDatados;
    private Alertas alertas;
    @JsonProperty("info_restricao")
    private String infoRestricao;
    @JsonProperty("dt_nascimento")
    private String dtNascimento;
    private int idade;
    @JsonProperty("pais_nascimento")
    private String paisNascimento;
    @JsonProperty("data_nascimento")
    private String dataNascimento;
    private String signo;
    private String cpf;
    @JsonProperty("nome_pai")
    private String nomePai;
    @JsonProperty("restricoes_bancarias")
    private Restricoes restricoesBancarias;
    @JsonProperty("restricoes_lojistas")
    private Restricoes restricoesLojistas;
    @JsonProperty("nome_mae")
    private String nomeMae;
    private String statusReceita;
    @JsonProperty("nome_completo")
    private String nomeCompleto;
}
