package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.FlowProcessing;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces.WebClientInterface;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.OrderEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.entities.PaymentEntity;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Customer;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Document;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Item;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Pix;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.OrderService;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Service
public class PagFlyClient extends FlowProcessing {

    @Autowired
    private WebClientInterface webClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;

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
        return webClient.post(baseUrl, "/transactions", getDefaultHeaders(), generateRequest(o))
                .map(r -> objectMapper.convertValue(r, PagFlyCreateTransactionResponseDTO.class));
    }

    @Override
    public Mono<Object> createOrder(RequestContext dto) {
        return orderService.createOrder(dto);
    }

    @Override
    protected <O> Object pay(RequestContext requestContext) {
        return null;
    }

    @Override
    protected void save() {

    }

    @Override
    public Mono<PaymentEntity> createPayment(OrderEntity order, Object response) {

        PagFlyCreateTransactionResponseDTO responseDTO = objectMapper
                .convertValue(response, PagFlyCreateTransactionResponseDTO.class);

        return paymentService.createPayment(PaymentEntity.builder()
                .createdAt(LocalDateTime.now())
                .person(order.getPerson())
                .status(responseDTO.getStatus())
                .order(order)
                .paymentMethod(responseDTO.getPaymentMethod())
                .qrCode(responseDTO.getPix().getQrcode())
                .trasactionId(responseDTO.getSecureId())
                .build());
    }

    @Override
    protected void get() {

    }

    @Override
    protected void bereau() {

    }

    private PagFlyCreateTransactionRequestDTO generateRequest(RequestContext o) {
        return PagFlyCreateTransactionRequestDTO.builder()
                .paymentMethod("PIX")
                .customer(Customer.builder()
                        .name(o.getName())
                        .email(o.getEmail())
                        .document(Document.builder()
                                .type("cpf")
                                .number(o.getFederalId())
                                .build())
                        .build())
                .amount(o.getAmount().longValue() * 100)
                .installments("1")
                .pix(Pix.builder()
                        .expireInDays(1)
                        .build())
                .items(List.of(Item.builder()
                        .title("Consulta CPF")
                        .quantity(1)
                        .unitPrice(o.getAmount().longValue() * 100)
                        .build()))
                .build();
    }

}
