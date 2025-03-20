package br.dev.guilhermecordeiro.consulta_cpfcnpj.services;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Mono<PaymentEntity> createPayment(PaymentEntity payment) {
        try {
            System.out.println(objectMapper.writeValueAsString(payment));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return paymentRepository.save(payment);
    }

    public Mono<PaymentEntity> getPaymentByTransactionId(String id) {
        return paymentRepository.findByTransactionId(id);
    }
}
