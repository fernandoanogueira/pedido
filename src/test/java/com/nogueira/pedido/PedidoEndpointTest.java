package com.nogueira.pedido;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nogueira.pedido.dto.CartaoDTO;
import com.nogueira.pedido.dto.PedidoDTO;
import com.nogueira.pedido.model.Pedido;
import com.nogueira.pedido.repository.PedidoRepository;
import com.nogueira.pedido.service.CadastroConsumerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PedidoEndpointTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@MockBean
	private PedidoRepository pedidoRepository;
	
	@MockBean
	private CadastroConsumerService cadastroConsumerService;

	@Autowired
	private MockMvc mockMvc;

	private Pedido pedido;
	
	private PedidoDTO pedidoDTO;

	@TestConfiguration
	static class Config {
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder();
		}
	}

	@Before
	public void setup() {
		pedido = new Pedido(1L, 1, 1, 1L, BigDecimal.valueOf(10L), new Date());
		
		pedidoDTO = new PedidoDTO(1L, 1L, 1L, 1L, BigDecimal.valueOf(10L), new Date());

		BDDMockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
	}

	@Test
	public void getPedidoExistente_DeveRetornarStatusCode200() {

		ResponseEntity<Pedido> response = restTemplate.getForEntity("/pedido/{id}", Pedido.class, pedido.getId());

		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void getPedidoInexistente_DeveRetornarStatusCode404() {

		ResponseEntity<PedidoDTO> response = restTemplate.getForEntity("/pedido/{id}", PedidoDTO.class, 256L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deleteQuandoPedidoExistir_DeveRetornarStatusCode200() {

		BDDMockito.doNothing().when(pedidoRepository).delete(pedido);

		ResponseEntity<PedidoDTO> exchange = restTemplate.exchange("/pedido/{id}", HttpMethod.DELETE, null, PedidoDTO.class,
				1L);

		Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void deleteQuandoPedidoNaoExistir_DeveRetornarStatusCode404() {

		ResponseEntity<PedidoDTO> exchange = restTemplate.exchange("/pedido/{id}", HttpMethod.DELETE, null, PedidoDTO.class,
				256L);

		Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deleteQuandoPedidoNaoExistir_DeveRetornarStatusCode404_MockMVC() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
				.delete("/pedido/{id}", 256L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void incluirQuandoCartaoNaoExiste_DeveRetornarStatusCode400() {
		
		BDDMockito.when(cadastroConsumerService.consultarCartao(1L)).thenReturn(null);
		
		ResponseEntity<PedidoDTO> response = restTemplate.postForEntity("/pedido/", pedidoDTO, PedidoDTO.class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void incluirCartaoExisteVinculoEmpregadoEmpresa_DeveRetornarStatusCode200() {
		
		BDDMockito.when(cadastroConsumerService.consultarCartao(1L))
			.thenReturn(new ResponseEntity<CartaoDTO>(new CartaoDTO(1L, "Cartao 1", "SC"), HttpStatus.OK));
		
		BDDMockito.when(cadastroConsumerService.consultarVinculoEmpresaEmpregado(1L, 1L))
			.thenReturn(true);
		
		BDDMockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedido);
		
		ResponseEntity<PedidoDTO> response = restTemplate.postForEntity("/pedido/", pedidoDTO, PedidoDTO.class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		
		Assertions.assertThat(response.getBody().getIdEmpregado()).isEqualTo(1L);
	}
	
}
