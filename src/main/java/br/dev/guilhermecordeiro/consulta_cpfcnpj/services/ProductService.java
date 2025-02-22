package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.ProductEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Flux<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<ProductEntity> createProduct(Object o) {
        return productRepository.save(objectMapper.convertValue(o, ProductEntity.class));
    }

}
