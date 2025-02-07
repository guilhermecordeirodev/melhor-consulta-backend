package br.dev.guilhermecordeiro.consulta_cpfcnpj.controllers;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.FlowProcessing;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.dto.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FlowController {

    private final FlowProcessing flowProcessing;

    @PostMapping("/generate-payment")
    public Mono generatePayment(@RequestBody RequestContext requestContext) {
        return flowProcessing.generatePayment(requestContext);
    }

    @GetMapping("/search/{orderId}")
    public Mono<Object> searchData(@PathVariable String orderId) {
        return flowProcessing.searchData(orderId);
    }


    //TODO: implementar callback como webhook
//    {
//        id: 2462404,
//                type: 'transaction',
//            objectId: 2462404,
//            url: 'https://midge-decent-shortly.ngrok-free.app/api/pagamentos/webhook',
//            data: {
//        id: 2462404,
//                amount: 100,
//                paidAmount: 100,
//                refundedAmount: 0,
//                tenantId: '3edb0ded-0277-4087-b7de-108e09fa466b',
//                companyId: 4250,
//                installments: 1,
//                paymentMethod: 'pix',
//                status: 'paid',
//                postbackUrl: null,
//                metadata: null,
//                traceable: false,
//                secureId: '87b3d6e3-6c7f-4972-9fad-74c6875197ef',
//                secureUrl: '87b3d6e3-6c7f-4972-9fad-74c6875197ef',
//                createdAt: '2025-02-07T04:33:00.522Z',
//                updatedAt: '2025-02-07T04:35:09.870Z',
//                paidAt: '2025-02-07T04:35:09.869Z',
//                refundedAt: null,
//                ip: null,
//                externalRef: null,
//                authorizationCode: null,
//                basePrice: null,
//                interestRate: null,
//                customer: {
//            id: 3987,
//                    name: 'Giovanni Keppelen',
//                    email: 'email@email.com',
//                    phone: '21997107940',
//                    birthdate: null,
//                    createdAt: '2024-10-11T02:14:58.395Z',
//                    externalRef: null,
//                    document: [Object],
//            address: [Object]
//        },
//        fee: {
//            netAmount: -52,
//                    estimatedFee: 152,
//                    fixedAmount: 0,
//                    spreadPercent: 0
//        },
//        pix: {
//            qrcode: '00020126850014br.gov.bcb.pix2563pix.voluti.com.br/qr/v3/at/8f3b297a-6185-42ae-a09a-0acfe74d3e0e5204000053039865802BR5925TRANSAC_INTERMEDIADOR_DE_6007GOIANIA62070503***63047C44',
//                    end2EndId: null,
//                    receiptUrl: null,
//                    expirationDate: '2025-02-09'
//        },
//        boleto: null,
//                card: null,
//                shipping: { fee: null, address: [Object] },
//        refusedReason: null,
//                items: [ [Object] ],
//        splits: [ [Object] ],
//        refunds: [],
//        delivery: {
//            status: 'waiting',
//                    trackingCode: null,
//                    createdAt: '2025-02-07T04:33:00.522Z',
//                    updatedAt: '2025-02-07T04:33:00.522Z'
//        },
//        payer: null
//    }
//    }
}