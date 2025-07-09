package com.sala.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sala.DTO.SalaDTO;
import com.sala.service.SalaService;

@RestController
@RequestMapping("/api/salas")
public class SalaRestController {
	
	@Autowired
    private SalaService salaService;

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> obtenerSala(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.obtenerSalaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listarTodas() {
        return ResponseEntity.ok(salaService.listarSalas());
    }

}
