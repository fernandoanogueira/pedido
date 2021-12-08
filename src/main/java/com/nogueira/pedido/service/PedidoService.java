package com.nogueira.pedido.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nogueira.pedido.dto.CartaoDTO;
import com.nogueira.pedido.dto.PedidoDTO;
import com.nogueira.pedido.model.Pedido;
import com.nogueira.pedido.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroConsumerService cadastroConsumerService;
	
	@Autowired
	private CalculatorService calculatorService;
	
	public PedidoDTO consultarPedidoPorId(Long id) {
		Pedido pedido = pedidoRepository.findById(id).orElse(null);
		return mapper.map(pedido, PedidoDTO.class);
	}

	public List<PedidoDTO> listarPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDTO> pedidosDTO = new ArrayList<>();
		pedidos.stream().forEach(pedido -> pedidosDTO.add(mapper.map(pedido, PedidoDTO.class)));
		return pedidosDTO;
	}

	public PedidoDTO inserirPedido(PedidoDTO pedidoDTO) {
		
		validarCartao(pedidoDTO);
		validarVinculoEmpregadoEmpresa(pedidoDTO);
		
		Pedido pedido = mapper.map(pedidoDTO, Pedido.class);
		
		return mapper.map(pedidoRepository.save(pedido), PedidoDTO.class);
		
	}

	private void validarCartao(PedidoDTO pedidoDTO) {
		CartaoDTO cartao = cadastroConsumerService.consultarCartao(pedidoDTO.getIdCartao()).getBody();
		if(cartao==null) {
			throw new RuntimeException("Cartão informado não é válido.");			
		}
	}

	private void validarVinculoEmpregadoEmpresa(PedidoDTO pedidoDTO) {
		if(!cadastroConsumerService
				.consultarVinculoEmpresaEmpregado(pedidoDTO.getIdEmpresa(), pedidoDTO.getIdEmpregado())) {
			throw new RuntimeException("Não há vínculo entre a empresa e o empregado informados.");
		}
	}

	public BigDecimal calcularMediaDosPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		Function<Pedido, BigDecimal> valorMapper = pedido -> pedido.getValor();
		BigDecimal valorTotal = pedidos.stream().map(valorMapper).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return calculatorService.divide(valorTotal.longValue(), pedidos.size());
	}

}
