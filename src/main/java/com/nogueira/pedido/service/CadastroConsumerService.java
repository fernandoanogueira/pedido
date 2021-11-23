package com.nogueira.pedido.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nogueira.pedido.dto.CartaoDTO;
import com.nogueira.pedido.dto.EmpregadoDTO;
import com.nogueira.pedido.dto.EmpresaDTO;

@Service
public class CadastroConsumerService {
	
	private static final String URL_CADASTRO_EMPRESA = "http://localhost:8081/empresa/";
	private static final String URL_CADASTRO_EMPREGADO = "http://localhost:8081/empregado/";
	private static final String URL_CADASTRO_CARTAO = "http://localhost:8081/cartao/";
	
	RestTemplate restTemplate = new RestTemplate();
	
	public EmpresaDTO consultarEmpresa(Long id) {
		String url = URL_CADASTRO_EMPRESA + id.toString(); 
		EmpresaDTO response = restTemplate.getForObject(url, EmpresaDTO.class);
		return response;
	}
	
	public EmpregadoDTO consultarEmpregado(Long id) {
		String url = URL_CADASTRO_EMPREGADO + id.toString(); 
		EmpregadoDTO response = restTemplate.getForObject(url, EmpregadoDTO.class);
		return response;
	}
	
	public CartaoDTO consultarCartao(Long id) {
		String url = URL_CADASTRO_CARTAO + id.toString(); 
		CartaoDTO response = restTemplate.getForObject(url, CartaoDTO.class);
		return response;
	}
	
	public boolean consultarVinculoEmpresaEmpregado(Long idEmpresa, Long idEmpregado) {
		String url = URL_CADASTRO_EMPREGADO 
				+ idEmpresa.toString()
				+"/"
				+ idEmpregado.toString()
				+"/vinculo"; 
		boolean response = restTemplate.getForObject(url, boolean.class);
		return response;
	}


}
