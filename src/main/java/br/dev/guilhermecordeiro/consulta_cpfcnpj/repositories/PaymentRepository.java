package br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<PaymentEntity, String> {
}
