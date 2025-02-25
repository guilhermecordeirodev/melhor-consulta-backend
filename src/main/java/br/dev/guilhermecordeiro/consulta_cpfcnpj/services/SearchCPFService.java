package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.CredlinkClient;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.FederalIdentificationRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.utils.StringMasker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class SearchCPFService implements Function<String, Mono<FederalIdentificationEntity>> {

    @Autowired
    private CredlinkClient client;
    @Autowired
    private FederalIdentificationRepository federalIdentificationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<FederalIdentificationEntity> apply(String cpf) {
        return buscarRegistro(cpf).map(t -> (FederalIdentificationEntity) StringMasker.maskObject(t));
    }

    private Mono<FederalIdentificationEntity> buscarRegistro(String cpf) {
        System.out.println(cpf);
        return federalIdentificationRepository.findByCpfCnpj(cpf)
                .doOnNext(entity -> System.out.println("Registro encontrado no banco: " + entity))
                .flatMap(Mono::just)
                .switchIfEmpty(
                        client.consultarCpf(cpf)
                                .flatMap(response -> {
                                    FederalIdentificationEntity entity = new FederalIdentificationEntity();
                                    entity.setDadosFromResponse(response);
                                    try {
                                        objectMapper.registerModule(new JavaTimeModule());
                                        String jsonResponse = objectMapper.writeValueAsString(response);
                                        entity.setDados(jsonResponse);
                                    } catch (Exception e) {
                                        System.err.println("Erro ao converter resposta para JSON: " + e.getMessage());
                                    }
                                    return federalIdentificationRepository.save(entity);
                                })
                                .doOnSuccess(entity -> System.out.println("Novo registro salvo: " + entity))
                );
    }
}
