package com.pelicula.service;

import java.util.List;

import com.pelicula.DTO.PeliculaDTO;
import com.pelicula.entity.Pelicula;

public interface PeliculaService {
	
	List<PeliculaDTO> listarPelicula();
	List<PeliculaDTO> listarPelicula(String baseUrl);

	    Pelicula getIdPelicula(Long id);

	    void crearActualizarPelicula(Pelicula pelicula);

	    void eliminarPelicula(Long id);
	    PeliculaDTO obtenerPorId(Long id);
	    List<PeliculaDTO> listarTodas();

	    List<PeliculaDTO> buscarPorCategoria(Integer categoriaId);
}
