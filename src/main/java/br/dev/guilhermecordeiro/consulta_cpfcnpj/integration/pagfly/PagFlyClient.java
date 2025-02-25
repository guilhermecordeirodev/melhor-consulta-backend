package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.FlowProcessing;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.ProductEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.UserEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.enums.StatusOrder;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Customer;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Document;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Item;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Pix;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.ProductRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.repositories.UserRepository;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.OrderService;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PagFlyClient extends FlowProcessing {

    @Autowired
    private WebClient webClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${pag-fly.base-url}")
    private String baseUrl;
    @Value("${pag-fly.private-key}")
    private String privateKey;
    @Value("${pag-fly.password}")
    private String password;

    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic ".concat(Base64.getEncoder()
                .encodeToString((privateKey + ":" + password).getBytes())));

        return headers;
    }

    @Override
    public Mono<PagFlyCreateTransactionResponseDTO> generatePaymentMethod(RequestContext o) {
        return generateRequest(o) // Mono<PagFlyCreateTransactionRequestDTO>
                .flatMap(requestBody -> webClient.post()
                        .uri(baseUrl + "/transactions")
                        .headers(httpHeaders ->
                                getDefaultHeaders().forEach((key, value) -> httpHeaders.put(key, List.of(value))))
                        .body(BodyInserters.fromValue(requestBody))
                        .retrieve()
                        .bodyToMono(PagFlyCreateTransactionResponseDTO.class)
                );
    }

    @Override
    public Mono<Object> createOrder(RequestContext dto) {
        return orderService.createOrder(dto);
    }

    @Override
    public Mono<PaymentEntity> createPayment(OrderEntity order, Object response) {

        PagFlyCreateTransactionResponseDTO responseDTO = objectMapper
                .convertValue(response, PagFlyCreateTransactionResponseDTO.class);

        return paymentService.createPayment(PaymentEntity.builder()
                .createdAt(LocalDateTime.now())
                .orderId(order.getId())
                .status(responseDTO.getStatus())
                .paymentMethod(responseDTO.getPaymentMethod())
                .qrCode(responseDTO.getPix().getQrcode())
                .transactionId(responseDTO.getSecureId())
                .build());
    }

    @Override
    public Mono<PaymentEntity> updatePayment(String id) {
        System.out.println(id);
        return paymentService.getPaymentByTransactionId(id)
                .doOnNext(e -> System.out.println("🔍 Pagamento encontrado: " + e)) // Verifica se o pagamento foi encontrado
                .flatMap(e -> {
                    e.setStatus("payed");
                    e.setPaidAt(LocalDateTime.now());
                    return paymentService.createPayment(e);
                })
                .doOnSuccess(e -> System.out.println("✅ Pagamento atualizado: " + e))
                .switchIfEmpty(Mono.error(new RuntimeException("❌ Pagamento não encontrado!")));
    }

    @Override
    public Mono<OrderEntity> updateOrder(String id) {
        return orderService.getOrderById(id).flatMap(orderEntity -> {
            orderEntity.setStatus(StatusOrder.PAGO);
            return orderService.saveOrder(orderEntity);
        });
    }

    @Override
    public Mono<OrderEntity> callback(Object callback) {
        PagFlyWebhookDTO dto = objectMapper.convertValue(callback, PagFlyWebhookDTO.class);
        return updatePayment(dto.getData().getSecureId()).flatMap(p -> updateOrder(p.getOrderId()));
    }

    private Mono<PagFlyCreateTransactionRequestDTO> generateRequest(RequestContext o) {
        return Mono.zip(
                userRepository.findById(o.getUserId()),
                productRepository.findById(o.getProductId())
        ).map(objects -> {
            UserEntity user = objects.getT1();
            ProductEntity product = objects.getT2();
            long value = product.getValue().longValue() * 100;

            return PagFlyCreateTransactionRequestDTO.builder()
                    .paymentMethod("pix")
                    .customer(Customer.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                            .document(Document.builder()
                                    .type("cpf")
                                    .number(o.getCpf())
                                    .build())
                            .build())
                    .amount(value)
                    .installments("1")
                    .pix(Pix.builder()
                            .expireInDays(1)
                            .build())
                    .items(List.of(Item.builder()
                            .title(product.getName())
                            .quantity(1)
                            .unitPrice(value)
                            .build()))
                    .build();
        });
    }

}
