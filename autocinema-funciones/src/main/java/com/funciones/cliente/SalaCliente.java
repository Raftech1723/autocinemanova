package com.funciones.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sala", url = "${servicio.sala.url}")
public interface SalaCliente {
  
	   @GetMapping("/{id}")
	    SalaDTO obtenerSala(@PathVariable("id") Long id);

	    @GetMapping
	    List<SalaDTO> listarSalas();
}
