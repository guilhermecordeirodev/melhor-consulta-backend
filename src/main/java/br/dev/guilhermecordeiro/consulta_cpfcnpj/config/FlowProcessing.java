package br.dev.guilhermecordeiro.consulta_cpfcnpj.config;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public abstract class FlowProcessing {

    public Mono<Object> generatePayment(RequestContext requestContext) {
        return Mono.zip(createOrder(requestContext),
                        generatePaymentMethod(requestContext)
                                .flatMap(this::createPayment))
                .map(Tuple2::getT2);
    }

    public <O> O searchData(String orderId) {
        System.out.println("Processando consulta...");
        get();
        boolean naoExiste = true;
        if (naoExiste) {
            bereau();
            save();
        }
        return null;
    }

    public abstract Mono<Object> generatePaymentMethod(Object o);

    public abstract Mono<Object> createOrder(RequestContext dto);

    abstract <O> Object pay(RequestContext requestContext);

    abstract void save();

    public abstract Mono<PaymentEntity> createPayment(Object payment);

    abstract void get();

    abstract void bereau();
}