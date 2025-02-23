package br.dev.guilhermecordeiro.consulta_cpfcnpj.config;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.enums.StatusOrder;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.PagFlyCreateTransactionResponseDTO;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.FederalIdentificationRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.OrderRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public abstract class FlowProcessing {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FederalIdentificationRepository federalIdentificationRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public Mono generatePayment(RequestContext requestContext) {
        return Mono.zip(createOrder(requestContext), generatePaymentMethod(requestContext))
                .flatMap(t -> createPayment((OrderEntity) t.getT1(), t.getT2()));
    }

    public Mono<Object> searchData(String orderId) {
        return paymentRepository.findById(orderId).flatMap(p -> orderRepository.findById(p.getOrderId())
                .flatMap(o -> {
                    if (o.getStatus() == StatusOrder.PAGO) {
                        return federalIdentificationRepository.findById(o.getFederalIdRequest());
                    }
                    return Mono.empty();
                }));
    }

    public abstract Mono<PagFlyCreateTransactionResponseDTO> generatePaymentMethod(RequestContext o);

    public abstract Mono<Object> createOrder(RequestContext dto);

    public abstract Mono<PaymentEntity> createPayment(OrderEntity order, Object response);

    public abstract Mono<PaymentEntity> updatePayment(String id);

    public abstract Mono<OrderEntity> updateOrder(String id);

    public abstract Mono<OrderEntity> callback(Object callback);

}