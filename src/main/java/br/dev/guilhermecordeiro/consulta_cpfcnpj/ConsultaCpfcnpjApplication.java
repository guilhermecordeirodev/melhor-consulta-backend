package br.dev.guilhermecordeiro.consulta_cpfcnpj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ConsultaCpfcnpjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaCpfcnpjApplication.class, args);
	}

}
