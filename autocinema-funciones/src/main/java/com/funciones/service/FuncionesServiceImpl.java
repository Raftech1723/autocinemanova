package com.funciones.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funciones.DTO.FuncionDTO;
import com.funciones.cliente.PeliculaDTO;
import com.funciones.cliente.SalaDTO;
import com.funciones.entity.Funciones;
import com.funciones.repository.FuncionesRepository;

@Service
public class FuncionesServiceImpl implements FuncionesService {
	
	@Autowired
	private FuncionesRepository funcionesRepository;

	@Override
	public List<Funciones> listarFunciones() {
		// TODO Auto-generated method stub
		return funcionesRepository.findAll();
	}

	@Override
	public Funciones getIdFunciones(Long id) {
		// TODO Auto-generated method stub
		return funcionesRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<FuncionDTO> construirFuncionesDTO(List<Funciones> funciones, List<PeliculaDTO> peliculas, List<SalaDTO> salas){
	    return funciones.stream().map(f -> {
	        FuncionDTO dto = new FuncionDTO();
	        dto.setId(f.getId());
	        dto.setFecha(f.getFecha()); 
	        dto.setHora(f.getHora());   
	        dto.setPelicula_id(f.getPelicula_id());
	        dto.setSala_id(f.getSala_id());

	        peliculas.stream()
	            .filter(p -> p.getId().equals(f.getPelicula_id()))
	            .findFirst()
	            .ifPresent(p -> dto.setTituloPelicula(p.getTitulo()));

	        salas.stream()
	            .filter(s -> s.getId().equals(f.getSala_id()))
	            .findFirst()
	            .ifPresent(s -> dto.setNombreSala(s.getNombre()));

	        return dto;
	    }).collect(Collectors.toList());
	}



	@Override
	public void crearActualizarFunciones(Funciones funciones) {
		Funciones fcn = new Funciones();
		fcn.setId(funciones.getId());
		fcn.setFecha(funciones.getFecha());
		fcn.setHora(funciones.getHora());
		fcn.setPelicula_id(funciones.getPelicula_id());
		fcn.setSala_id(funciones.getSala_id());
		funcionesRepository.save(fcn);
	}

	@Override
	public void eliminarFunciones(Long id) {
		funcionesRepository.deleteById(id);
	}
	
	 @Override
	    public Funciones buscarPorId(Long id) {
	        return funcionesRepository.findById(id).orElse(null);
	    }


	@Override
	    public void guardar(Funciones funciones){
	        funcionesRepository.save(funciones);
	    }

}
