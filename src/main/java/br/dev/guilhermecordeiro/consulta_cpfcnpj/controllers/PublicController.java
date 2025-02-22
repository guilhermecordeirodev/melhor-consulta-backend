package br.dev.guilhermecordeiro.consulta_cpfcnpj.controllers;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.ProductEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.ProductService;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.SearchCPFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SearchCPFService searchCPFService;

    @GetMapping("/products")
    public Flux<ProductEntity> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/cpf/{cpf}")
    public Mono<FederalIdentificationEntity> getCpf(@PathVariable String cpf) {
        return searchCPFService.apply(cpf);
    }

}
