package com.sala.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sala.DTO.SalaDTO;
import com.sala.entity.Sala;
import com.sala.repository.SalaRepository;

@Service
public class SalaServiceImpl implements SalaService {
	
	@Autowired
    private SalaRepository salaRepository;
	
	@Override
    public SalaDTO obtenerSalaPorId(Long id) {
        Sala sala = salaRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe sala"));
        SalaDTO dto = new SalaDTO();
        dto.setId(sala.getId());
        dto.setNombre(sala.getNombre());
        return dto;
    }

    @Override
    public List<SalaDTO> listarSalas() {
        return salaRepository.findAll().stream().map(s -> {
            SalaDTO dto = new SalaDTO();
            dto.setId(s.getId());
            dto.setNombre(s.getNombre());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Sala> listarSala() {
        return salaRepository.findAll();
    }

    @Override
    public Sala getIdSala(Long id) {
        return salaRepository.findById(id).orElse(null);
    }

    @Override
    public void crearActualizarSala(Sala sala) {
        Sala s = new Sala();
        s.setId(sala.getId());
        s.setNombre(sala.getNombre());
        s.setCapacidad(sala.getCapacidad());
        s.setSede_id(sala.getSede_id());
        salaRepository.save(s);
    }

    @Override
    public void eliminarSala(Long id) {
        salaRepository.deleteById(id);
    }

}
