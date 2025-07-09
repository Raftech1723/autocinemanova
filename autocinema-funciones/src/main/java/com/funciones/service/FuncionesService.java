package com.funciones.service;

import java.util.List;

import com.funciones.DTO.FuncionDTO;
import com.funciones.cliente.PeliculaDTO;
import com.funciones.cliente.SalaDTO;
import com.funciones.entity.Funciones;

public interface FuncionesService {
	
	List<Funciones> listarFunciones();
	Funciones getIdFunciones(Long id);
	void crearActualizarFunciones(Funciones funciones);
	void eliminarFunciones(Long id);
    Funciones buscarPorId(Long id); 
    void guardar(Funciones funciones);
    List<FuncionDTO> construirFuncionesDTO(List<Funciones> funciones, List<PeliculaDTO> peliculas, List<SalaDTO> salas);

}
