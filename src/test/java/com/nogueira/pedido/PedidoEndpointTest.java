package com.nogueira.pedido;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
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

import com.nogueira.pedido.model.Pedido;
import com.nogueira.pedido.repository.PedidoRepository;

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

	@Autowired
	private MockMvc mockMvc;

	private Pedido pedido;

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

		BDDMockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
	}

	@Test
	public void getPedidoExistente_DeveRetornarStatusCode200() {

		ResponseEntity<Pedido> response = restTemplate.getForEntity("/pedido/{id}", Pedido.class, pedido.getId());

		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void getPedidoInexistente_DeveRetornarStatusCode404() {

		ResponseEntity<Pedido> response = restTemplate.getForEntity("/pedido/{id}", Pedido.class, 256L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deleteQuandoPedidoExistir_DeveRetornarStatusCode200() {

		BDDMockito.doNothing().when(pedidoRepository).delete(pedido);

		ResponseEntity<Pedido> exchange = restTemplate.exchange("/pedido/{id}", HttpMethod.DELETE, null, Pedido.class,
				1L);

		Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void deleteQuandoPedidoNaoExistir_DeveRetornarStatusCode404() {

		ResponseEntity<Pedido> exchange = restTemplate.exchange("/pedido/{id}", HttpMethod.DELETE, null, Pedido.class,
				256L);

		Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deleteQuandoPedidoNaoExistir_DeveRetornarStatusCode404_MockMVC() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
				.delete("/pedido/{id}", 256L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
}
