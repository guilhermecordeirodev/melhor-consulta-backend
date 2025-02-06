package br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FederalIdentificationRepository extends ReactiveMongoRepository<FederalIdentificationEntity, String> {
}
