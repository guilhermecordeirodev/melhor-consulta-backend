package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.FlowProcessing;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PersonEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.OrderRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private PersonRepository personRepository;

    public Mono<Object> createOrder(RequestContext dto) {
        return personRepository.save(PersonEntity.builder()
                .email(dto.getEmail())
                .federalId(dto.getFederalId())
                .name(dto.getName())
                .build())
                .flatMap(personEntity -> repository
                        .save(OrderEntity.builder()
                                .federalIdRequest(dto.getFederalIdRequest())
                                .person(personEntity)
                                .amount(dto.getAmount())
                                .status("PENDENTE")
                                .build())
                );
    }

    public Mono<OrderEntity> getOrderById(String id) {
        return repository.findById(id);
    }

    public Mono<OrderEntity> saveOrder(OrderEntity order) {
        return repository.save(order);
    }

}
