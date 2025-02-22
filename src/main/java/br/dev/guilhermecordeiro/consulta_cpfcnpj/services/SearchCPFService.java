package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.CredlinkClient;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.FederalIdentificationRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.utils.StringMasker;
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

    @Override
    public Mono<FederalIdentificationEntity> apply(String cpf) {
        return buscarRegistro(cpf).map(t -> (FederalIdentificationEntity) StringMasker.maskObject(t));
    }

    private Mono<FederalIdentificationEntity> buscarRegistro(String cpf) {
        return federalIdentificationRepository.findByCpfCnpj(cpf).switchIfEmpty(
                client.consultarCpf(cpf).flatMap(response -> {
                    FederalIdentificationEntity federalIdentificationEntity = new FederalIdentificationEntity();
                    federalIdentificationEntity.setDadosFromResponse(response);
                    return federalIdentificationRepository.save(federalIdentificationEntity);
                })
        );
    }
}
