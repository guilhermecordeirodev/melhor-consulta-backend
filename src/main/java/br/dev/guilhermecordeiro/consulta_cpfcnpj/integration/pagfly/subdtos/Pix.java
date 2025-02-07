package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.pagfly.subdtos;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Pix {
    private String qrcode;
    private String end2EndId;
    private String receiptUrl;
    private String expirationDate;
    private long expireInDays;
}