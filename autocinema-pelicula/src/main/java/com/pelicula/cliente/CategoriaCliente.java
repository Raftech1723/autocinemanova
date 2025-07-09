package com.pelicula.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "categoria", url = "${servicio.categoria.url}")
public interface CategoriaCliente {

	@GetMapping("")
    List<CategoriaDTO> listarCategorias();


    @GetMapping("/{id}/imagen")
    String imagenPorCategoriaId(@PathVariable("id") Long id);

}
