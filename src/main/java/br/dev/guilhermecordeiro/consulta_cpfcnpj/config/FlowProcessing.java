package br.dev.guilhermecordeiro.consulta_cpfcnpj.config;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.PagFlyCreateTransactionResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public abstract class FlowProcessing {

    public Mono generatePayment(RequestContext requestContext) {
        return Mono.zip(createOrder(requestContext),
                        generatePaymentMethod(requestContext)).flatMap(t -> createPayment((OrderEntity) t.getT1(), t.getT2()));
    }

    public <O> O searchData(String orderId) {
        return null;
    }

    public abstract Mono<PagFlyCreateTransactionResponseDTO> generatePaymentMethod(RequestContext o);

    public abstract Mono<Object> createOrder(RequestContext dto);

    public abstract Mono<PaymentEntity> createPayment(OrderEntity order, Object response);

    public abstract Mono<PaymentEntity> updatePayment(String id);

    public abstract Mono<OrderEntity> updateOrder(String id);

    public abstract void callback(Object callback);

}