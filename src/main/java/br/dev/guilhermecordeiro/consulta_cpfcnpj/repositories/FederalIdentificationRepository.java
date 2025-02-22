package br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FederalIdentificationRepository extends ReactiveMongoRepository<FederalIdentificationEntity, String> {

    Mono<FederalIdentificationEntity> findByCpfCnpj(String cpfCnpj);
}
