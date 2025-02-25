package br.dev.guilhermecordeiro.consulta_cpfcnpj.entities;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.CredilinkResponse;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos.ConsultaCCF;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos.CredilinkWebService;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.dtos.Telefone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "federal_identification")
public class FederalIdentificationEntity {
    @Id
    private String id;
    @Indexed
    private String cpfCnpj;
    private String nome;
    private String email;
    private String telefone;
    private String logradouro;
    private String cep;
    private String cidade;
    private String estado;
    private String quantidadeConsultas;
    private String statusReceita;
    private String nomeMae;
    private String nomePai;
    private String dataNascimento;
    private String paisNascimento;
    private String quantidadeEmpresas;
    private String constaObito;

    @JsonIgnore
    private String dados;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Mapeia os dados da API Credilink para a entidade FederalIdentificationEntity
     */
    public void setDadosFromResponse(CredilinkResponse response) {
        if (response == null || response.getCredilinkWebservice() == null) {
            return;
        }

        CredilinkWebService credilink = response.getCredilinkWebservice();
        ConsultaCCF ccf619 = credilink.getConsultaCcf619();

        if (ccf619 != null) {
            this.cpfCnpj = String.valueOf(ccf619.getCpf());
            this.nome = ccf619.getNomeCompleto();
            this.statusReceita = ccf619.getStatusReceita();
            this.dataNascimento = ccf619.getDataNascimento();
            this.paisNascimento = ccf619.getPaisNascimento();
            this.nomeMae = ccf619.getNomeMae();
            this.nomePai = ccf619.getNomePai();
            this.constaObito = credilink.getObito().size() > 0 ? "Sim" : "Não";
        }

        // Dados de telefone
        if (credilink.getConsultaTelefoneProprietario() != null
                && credilink.getConsultaTelefoneProprietario().getTelefone() != null
                && !credilink.getConsultaTelefoneProprietario().getTelefone().isEmpty()) {
            this.telefone = credilink.getConsultaTelefoneProprietario().getTelefone().get(0).getTelefone();
        }

        // Endereço (se disponível)
        if (credilink.getConsultaTelefoneProprietario() != null
                && credilink.getConsultaTelefoneProprietario().getTelefone() != null
                && !credilink.getConsultaTelefoneProprietario().getTelefone().isEmpty()) {
            Telefone telefoneProprietario = credilink.getConsultaTelefoneProprietario().getTelefone().get(0);
            this.logradouro = telefoneProprietario.getEndereco() + telefoneProprietario.getNumero();
            this.cep = telefoneProprietario.getCep();
            this.cidade = telefoneProprietario.getCidade();
            this.estado = telefoneProprietario.getUf();
        }

        // Contagem de consultas realizadas
        if (credilink.getConsultasRealizadas() != null && credilink.getConsultasRealizadas().getConsulta() != null) {
            this.quantidadeConsultas = String.valueOf(credilink.getConsultasRealizadas().getConsulta().size());
        }

        // Quantidade de empresas (sociedades)
        if (credilink.getDadosSociedades() != null && credilink.getDadosSociedades().getSociedades() != null) {
            this.quantidadeEmpresas = String.valueOf(credilink.getDadosSociedades().getSociedades().size());
        }
    }
}
