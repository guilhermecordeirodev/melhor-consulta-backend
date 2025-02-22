package br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink;

import br.dev.guilhermecordeiro.consulta_cpfcnpj.config.interfaces.WebClientInterface;
import br.dev.guilhermecordeiro.consulta_cpfcnpj.integration.credlink.CredilinkResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CredlinkClient {

    @Value("${credlink.base-url}")
    private String baseUrl;
    @Value("${credlink.user}")
    private String usuario;
    @Value("${credlink.password}")
    private String senha;
    @Value("${credlink.sigla}")
    private String sigla;

    @Autowired
    private WebClientInterface webClientInterface;

    public Mono<CredilinkResponse> consultarCpf(String cpf) {
        return webClientInterface.getRetrieve(
                    baseUrl,
                    "/IntegracaoRest/webresources/integracao/completo",
                    generateParams(cpf))
                .bodyToMono(CredilinkResponse.class);
    }

    private String generateParams(String cpf) {
        return "?usuario=".concat(usuario)
                .concat("&senha=").concat(senha)
                .concat("&sigla=").concat(sigla)
                .concat("&cpfcnpj=").concat(cpf)
                .concat("&telefone&nome");
    }
}
