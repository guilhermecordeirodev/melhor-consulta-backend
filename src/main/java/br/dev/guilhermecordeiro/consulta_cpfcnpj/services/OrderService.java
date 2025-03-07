package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.enums.StatusOrder;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.OrderRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public Mono<Object> createOrder(RequestContext dto) {
        return productRepository.findById(dto.getProductId()).flatMap(productEntity -> repository.save(OrderEntity.builder()
                        .amount(productEntity.getValue())
                        .status(StatusOrder.PENDENTE)
                        .federalIdRequest(dto.getFederalId())
                        .userId(dto.getUserId())
                        .productId(dto.getProductId())
                .build()));
    }

    public Mono<OrderEntity> getOrderById(String id) {
        return repository.findById(id);
    }

    public Mono<OrderEntity> saveOrder(OrderEntity order) {
        return repository.save(order);
    }

    public Mono<OrderEntity> getOrderByPaymentId(String id) {
        return repository.findByPaymentId(id);
    }

}
