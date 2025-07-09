package com.usuario.controller;

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

import com.usuario.entity.Usuario;
import com.usuario.repository.UsuarioRepository;
import com.usuario.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
    private UsuarioService usuarioService;
 
	@Autowired
    private UsuarioRepository usuarioRepository;
	
		
    
    
	// Mostrar lista y formulario
	    @GetMapping
	    public String mostrarFormulario(Model model, HttpServletRequest request) {
	        model.addAttribute("usuario", new Usuario());
	        return "usuarios";
	    }

	    @GetMapping("/buscar")
	    public String buscarUsuarioPorId(@RequestParam(name = "id", required = false) Long id, Model model, HttpServletRequest request) {
	        List<Usuario> usuario;

	        if (id != null) {
	            Optional<Usuario> usuarios = usuarioRepository.findById(id);
	            usuario = usuarios.map(Collections::singletonList).orElse(Collections.emptyList());
	        } else {
	            usuario = usuarioRepository.findAll();
	        }

	        model.addAttribute("usuario", new Usuario());
	        model.addAttribute("listaUsuario", usuario);
	        return "usuarios";
	    }

	    // Guardar o actualizar usuario
	    @PostMapping("/guardar")
	    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
	        usuarioService.crearActualizarUsuario(usuario);
	        String forwardedHost = request.getHeader("X-Forwarded-Host");
	        String forwardedProto = request.getHeader("X-Forwarded-Proto");

	        String baseUrl;
	        if (forwardedHost != null && forwardedProto != null) {
	            baseUrl = forwardedProto + "://" + forwardedHost;
	        } else {
	            baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	        }

	        return "redirect:" + baseUrl + "/usuarios";
	    }

	    // Cargar datos en formulario para editar
	    @GetMapping("/editar/{id}")
	    public String editarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {                         
	        Usuario usuario = usuarioService.getIdUsuario(id);
	        model.addAttribute("usuario", usuario);
	        model.addAttribute("listaUsuario", usuarioService.listarUsuario());
	        return "usuarios";
	    }

	    // Eliminar usuario
	    @GetMapping("/eliminar/{id}")
	    public String eliminarUsuario(@PathVariable Long id, HttpServletRequest request) {
	        usuarioService.eliminarUsuario(id);
	        String forwardedHost = request.getHeader("X-Forwarded-Host");
	        String forwardedProto = request.getHeader("X-Forwarded-Proto");

	        String baseUrl;
	        if (forwardedHost != null && forwardedProto != null) {
	            baseUrl = forwardedProto + "://" + forwardedHost;
	        } else {
	            baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	        }

	        return "redirect:" + baseUrl + "/usuarios";
	    }
}


