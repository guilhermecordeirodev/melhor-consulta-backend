package br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderEntity, String> {
}
