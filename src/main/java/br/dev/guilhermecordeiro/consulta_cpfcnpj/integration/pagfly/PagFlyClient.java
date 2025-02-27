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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
        String credentials = privateKey + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + encodedAuth);
        headers.put("Content-Type", "application/json"); // Certifique-se de que este header está presente

        return headers;
    }


    @Override
    public Mono<PagFlyCreateTransactionResponseDTO> generatePaymentMethod(RequestContext o) {
        return generateRequest(o) // Mono<PagFlyCreateTransactionRequestDTO>
                .flatMap(requestBody -> webClient.post()
                        .uri(baseUrl + "/transactions")
                        .headers(httpHeaders -> {
                            Map<String, String> headers = getDefaultHeaders();
                            headers.forEach((key, value) -> {
                                httpHeaders.put(key, List.of(value));
                            });
                        })
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
                .orderId(order.getId())
                .status(responseDTO.getStatus())
                .paymentMethod(responseDTO.getPaymentMethod())
                .qrCode(responseDTO.getPix().getQrcode())
                .transactionId(responseDTO.getSecureId())
                .build());
    }

    @Override
    public Mono<PaymentEntity> updatePayment(String id) {
        return paymentService.getPaymentByTransactionId(id)
                .flatMap(e -> {
                    e.setStatus("payed");
                    return paymentService.createPayment(e);
                });
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
        ).handle((objects, sink) -> {
            try {
                UserEntity user = objects.getT1();
                ProductEntity product = objects.getT2();
                long value = product.getValue().longValue() * 100;

                Document document = new Document();
                document.setNumber("25653915017");
                document.setType("cpf");

                Customer customer = new Customer();
                customer.setName(user.getName());
                customer.setEmail(user.getEmail());
//                customer.setDocument("{" +
//                        "\"type\":\"cpf\"," +
//                        "\"number\":\"25653915017\"" +
//                        "}");
                customer.setDocument(document);

                Pix pix = new Pix();
                pix.setExpireInDays(1L);

                List<Item> items = new ArrayList<>();
                Item item = new Item();
                item.setTitle(product.getName());
                item.setQuantity(1);
                item.setUnitPrice(value);
                items.add(item);

                PagFlyCreateTransactionRequestDTO r = new PagFlyCreateTransactionRequestDTO();
                r.setPaymentMethod("pix");
                r.setCustomer(customer);
                r.setAmount(value);
                r.setInstallments("1");
                r.setPix(pix);
                r.setItems(items);

                System.out.println(objectMapper.writeValueAsString(r));
                sink.next(r);
            } catch (Exception e) {
                log.error(e.getMessage());
                sink.error(e);
            }
        });
    }

}
