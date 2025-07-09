package com.funciones.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "pelicula", url = "${servicio.pelicula.url}")
public interface PeliculaCliente {

  @GetMapping("/{id}")
  PeliculaDTO obtenerPelicula(@PathVariable("id") Long id);

  @GetMapping
  List<PeliculaDTO> listarTodas();
}
