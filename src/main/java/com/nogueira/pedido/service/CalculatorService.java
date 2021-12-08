package com.nogueira.pedido.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nogueira.pedido.client.calculator.AddResponse;
import com.nogueira.pedido.client.calculator.CalculatorClient;
import com.nogueira.pedido.client.calculator.DivideResponse;
import com.nogueira.pedido.client.calculator.MultiplyResponse;
import com.nogueira.pedido.client.calculator.SubtractResponse;

@Service
public class CalculatorService {

	@Autowired
	private CalculatorClient calculatorClient;

	public long add(long firstNumber, long secondNumber) {
		AddResponse response = calculatorClient.add(firstNumber, secondNumber);
		return response.getAddReturn();
	}

	public long subtract(long firstNumber, long secondNumber) {
		SubtractResponse response = calculatorClient.subtract(firstNumber, secondNumber);
		return response.getSubtractReturn();
	}

	public BigDecimal divide(long firstNumber, long secondNumber) {
		DivideResponse response = calculatorClient.divide(firstNumber, secondNumber);
		return response.getDivideReturn();
	}

	public long multiply(long firstNumber, long secondNumber) {
		MultiplyResponse response = calculatorClient.multiply(firstNumber, secondNumber);
		return response.getMultiplyReturn();
	}

}
