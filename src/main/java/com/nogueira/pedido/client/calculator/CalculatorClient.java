package com.nogueira.pedido.client.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CalculatorClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(CalculatorClient.class);

	public AddResponse add(Long firstNumber, Long secondNumber) {
		Add request = new Add();
		request.setFirstNumber(firstNumber);
		request.setSecondNumber(secondNumber);
		log.info("Requesting add");
		return (AddResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}
	
	public SubtractResponse subtract(Long firstNumber, Long secondNumber) {
		Subtract request = new Subtract();
		request.setFirstNumber(firstNumber);
		request.setSecondNumber(secondNumber);
		log.info("Requesting subtract");
		return (SubtractResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}
	
	public DivideResponse divide(Long firstNumber, Long secondNumber) {
		Divide request = new Divide();
		request.setFirstNumber(firstNumber);
		request.setSecondNumber(secondNumber);
		log.info("Requesting divide");
		return (DivideResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}
	
	public MultiplyResponse multiply(Long firstNumber, Long secondNumber) {
		Multiply request = new Multiply();
		request.setFirstNumber(firstNumber);
		request.setSecondNumber(secondNumber);
		log.info("Requesting multiply");
		return (MultiplyResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}	

}
