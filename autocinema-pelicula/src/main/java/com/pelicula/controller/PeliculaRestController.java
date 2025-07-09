package com.pelicula.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelicula.DTO.PeliculaDTO;
import com.pelicula.service.PeliculaService;

	
	@RestController
	@RequestMapping("/api/peliculas")
	public class PeliculaRestController {

		 	@Autowired
		    private PeliculaService peliculaService;

		    @GetMapping("/{id}")
		    public ResponseEntity<PeliculaDTO> getById(@PathVariable Long id) {
		        return ResponseEntity.ok(peliculaService.obtenerPorId(id));
		    }

		    @GetMapping
		    public ResponseEntity<List<PeliculaDTO>> getAll() {
		        return ResponseEntity.ok(peliculaService.listarTodas());
		    }

}
