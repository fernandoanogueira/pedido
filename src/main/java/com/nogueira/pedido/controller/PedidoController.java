package com.nogueira.pedido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nogueira.pedido.dto.PedidoDTO;
import com.nogueira.pedido.service.PedidoService;

@RestController
public class PedidoController {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping(value = "/pedido/{id}")
	public PedidoDTO consultarPedidoPorId(@PathVariable(required=true) Long id){
		return service.consultarPedidoPorId(id);
	}
	
	@GetMapping(value = "/pedido/")
	public List<PedidoDTO> listarPedidos(){
		return service.listarPedidos();
	}
	
	@PutMapping(value= "/pedido/")
	public PedidoDTO inserirPedido(@RequestBody PedidoDTO pedido) {
		return service.inserirPedido(pedido);
	}

}
