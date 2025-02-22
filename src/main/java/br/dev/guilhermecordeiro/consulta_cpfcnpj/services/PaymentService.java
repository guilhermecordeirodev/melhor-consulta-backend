package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Mono<PaymentEntity> createPayment(PaymentEntity payment) {
        return paymentRepository.save(payment);
    }

    public Mono<PaymentEntity> getPaymentByTransactionId(String id) {
        System.out.println(id);
        return paymentRepository.findByTransactionId(id);
    }
}
