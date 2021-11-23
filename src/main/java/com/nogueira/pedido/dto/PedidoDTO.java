package com.nogueira.pedido.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PedidoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long idEmpresa;
	
	private Long idEmpregado;
	
	private Long idCartao;
	
	private BigDecimal valor;
	
	private Date dataCredito;
	

}
