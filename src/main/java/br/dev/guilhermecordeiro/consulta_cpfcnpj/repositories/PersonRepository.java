package br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PersonEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<PersonEntity, String> {
}
