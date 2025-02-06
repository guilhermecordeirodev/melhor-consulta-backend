package br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.ParametersEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends ReactiveMongoRepository<ParametersEntity, String> {
}
