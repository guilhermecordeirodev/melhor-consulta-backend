package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.CredlinkClient;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.FederalIdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class MaskData implements Function<String, Mono<FederalIdentificationEntity>> {

    @Autowired
    private CredlinkClient client;
    @Autowired
    private FederalIdentificationRepository federalIdentificationRepository;

    @Override
    public Mono<FederalIdentificationEntity> apply(String cpf) {
        return federalIdentificationRepository.findByCpfCnpj(cpf);
    }
}
