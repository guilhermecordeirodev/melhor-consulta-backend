package br.dev.guilhermecordeiro.consulta_cpfcnpj.enums;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.FederalIdentificationEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.MaskData;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.SearchCPFService;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Getter
public enum ExisteEnum {
    EXISTE(new MaskData()),
    NAO_EXISTE(new SearchCPFService());

    private final Function<String, Mono<FederalIdentificationEntity>> function;

    ExisteEnum(Function<String, Mono<FederalIdentificationEntity>> function) {
        this.function = function;
    }
}
