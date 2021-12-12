package com.nogueira.pedido.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<PedidoDTO> consultarPedidoPorId(@PathVariable(required=true) Long id){
		PedidoDTO pedido = service.consultarPedidoPorId(id);
		if(pedido!=null)
			return ResponseEntity.ok(pedido);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/pedido/")
	public ResponseEntity<List<PedidoDTO>> listarPedidos(){
		return ResponseEntity.ok(service.listarPedidos());
	}
	
	@PutMapping(value= "/pedido/")
	public ResponseEntity<PedidoDTO> inserirPedido(@RequestBody PedidoDTO pedido) {
		try {
			return ResponseEntity.ok(service.inserirPedido(pedido));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(value="/pedido/media")
	public ResponseEntity<BigDecimal> retornarTotalDePedidos() {
		return ResponseEntity.ok(service.calcularMediaDosPedidos());
	}
	
	@DeleteMapping(value= "/pedido/{id}")
	public ResponseEntity<PedidoDTO> excluirPedido(@PathVariable Long id) {
		PedidoDTO pedido = service.excluirPedido(id);
		
		if(pedido!=null) 
			return ResponseEntity.ok().build();
		else 
			return ResponseEntity.notFound().build();
		
	}

}
