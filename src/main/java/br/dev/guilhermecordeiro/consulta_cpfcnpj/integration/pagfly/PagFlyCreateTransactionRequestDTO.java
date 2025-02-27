package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Customer;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Item;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos.Pix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagFlyCreateTransactionRequestDTO {
    private String paymentMethod;
    private Customer customer;
    private Long amount;
    private String installments;
    private Pix pix;
    private List<Item> items;
}
