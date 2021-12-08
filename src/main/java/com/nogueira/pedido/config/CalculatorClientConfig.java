package com.nogueira.pedido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.nogueira.pedido.client.calculator.CalculatorClient;

@Configuration
public class CalculatorClientConfig {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this is the package name specified in the <generatePackage> specified in pom.xml
		marshaller.setContextPath("com.nogueira.pedido.client.calculator");
		return marshaller;
	}

	@Bean
	public CalculatorClient soapConnector(Jaxb2Marshaller marshaller) {
		CalculatorClient client = new CalculatorClient();
		client.setDefaultUri("http://localhost:8080/CalculatorService/services/Calculator");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
