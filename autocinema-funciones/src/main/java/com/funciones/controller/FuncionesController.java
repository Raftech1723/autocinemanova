package com.funciones.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.funciones.DTO.FuncionDTO;
import com.funciones.cliente.PeliculaCliente;
import com.funciones.cliente.PeliculaDTO;
import com.funciones.cliente.SalaCliente;
import com.funciones.cliente.SalaDTO;
import com.funciones.entity.Funciones;
import com.funciones.repository.FuncionesRepository;
import com.funciones.service.FuncionesService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/funciones")
public class FuncionesController {
	
	@Autowired
    private FuncionesService funcionesService;

	@Autowired
    private PeliculaCliente peliculaCliente;

    @Autowired
    private SalaCliente salaCliente;
    
    @Autowired
    private FuncionesRepository funcionesRepository;
    
 // ✅ Método para construir dinámicamente el baseUrl
    private String obtenerBaseUrl(HttpServletRequest request) {
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        String forwardedHost = request.getHeader("X-Forwarded-Host");

        if (forwardedProto != null && forwardedHost != null) {
            return forwardedProto + "://" + forwardedHost;
        }

        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
    
    @GetMapping
    public String mostrarFormulario(Model model, HttpServletRequest request) {
        List<PeliculaDTO> peliculas = peliculaCliente.listarTodas();
        List<SalaDTO> salas = salaCliente.listarSalas();
        model.addAttribute("funciones", new Funciones());
        model.addAttribute("listaPelicula", peliculas);
        model.addAttribute("listaSala", salas);
        model.addAttribute("baseUrl", obtenerBaseUrl(request));
        return "funciones";
    }


    
    @GetMapping("/buscar")
    public String buscarFuncionesPorId(@RequestParam(name = "id", required = false) Long id,
                                       Model model, HttpServletRequest request) {
        List<Funciones> funciones;

        if (id != null) {
            Optional<Funciones> funcion = funcionesRepository.findById(id);
            funciones = funcion.map(Collections::singletonList).orElse(Collections.emptyList());
        } else {
            funciones = funcionesRepository.findAll();
        }

        List<PeliculaDTO> peliculas = peliculaCliente.listarTodas();
        List<SalaDTO> salas = salaCliente.listarSalas();

        List<FuncionDTO> funcionesDTO = funcionesService.construirFuncionesDTO(funciones, peliculas, salas);

        model.addAttribute("funciones", new Funciones());
        model.addAttribute("listaFunciones", funcionesDTO);
        model.addAttribute("listaPelicula", peliculas);
        model.addAttribute("listaSala", salas);
        model.addAttribute("baseUrl", obtenerBaseUrl(request));
        return "funciones";
    }
    @PostMapping("/guardar")
    public String guardarFunciones(@ModelAttribute Funciones funciones, HttpServletRequest request) {
        funcionesService.crearActualizarFunciones(funciones);
        return "redirect:" + obtenerBaseUrl(request) + "/funciones";
    }

    @GetMapping("/editar/{id}")
    public String editarFunciones(@PathVariable Long id, Model model, HttpServletRequest request) {
        Funciones funcion = funcionesService.getIdFunciones(id);

        List<PeliculaDTO> peliculas = peliculaCliente.listarTodas();
        List<SalaDTO> salas = salaCliente.listarSalas();
        List<Funciones> funciones = funcionesRepository.findAll();
        List<FuncionDTO> funcionesDTO = funcionesService.construirFuncionesDTO(funciones, peliculas, salas);

        model.addAttribute("funciones", funcion); 
        model.addAttribute("listaFunciones", funcionesDTO);
        model.addAttribute("listaPelicula", peliculas);
        model.addAttribute("listaSala", salas);
        model.addAttribute("baseUrl", obtenerBaseUrl(request));
        return "funciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarFunciones(@PathVariable Long id, HttpServletRequest request) {
        funcionesService.eliminarFunciones(id);
        return "redirect:" + obtenerBaseUrl(request) + "/funciones";
    }

}
