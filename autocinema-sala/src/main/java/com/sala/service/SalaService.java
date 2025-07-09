package com.sala.service;

import java.util.List;

import com.sala.DTO.SalaDTO;
import com.sala.entity.Sala;

public interface SalaService {
	
	List<Sala> listarSala();
	Sala getIdSala(Long id);
	void crearActualizarSala(Sala sala);
	void eliminarSala(Long id);
	SalaDTO obtenerSalaPorId(Long id);
    List<SalaDTO> listarSalas();

}
