package com.categoria.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.categoria.entity.Categoria;
import com.categoria.entity.ImagenCategoria;
import com.categoria.repository.CategoriaRepository;
import com.categoria.repository.ImagenCategoriaRepository;
import com.categoria.service.CategoriaService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
    private CategoriaService categoriaService;
    
	@Autowired
    private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ImagenCategoriaRepository imagenCategoriaRepository;
    
	/* Método para construir dinámicamente el baseUrl
	private String obtenerBaseUrl(HttpServletRequest request) {
	    String scheme = request.getScheme();             // http o https
	    String serverName = request.getServerName();     // localhost o nombre del host
	    int serverPort = request.getServerPort();        // 9090 o el puerto actual

	    return scheme + "://" + serverName + ":" + serverPort;
	} */


	@GetMapping("/{categoriaId}/imagen")
    public ResponseEntity<String> obtenerImagenCategoria(@PathVariable Long categoriaId) {
        List<ImagenCategoria> imagenes = imagenCategoriaRepository.findByCategoriaId(categoriaId);
        if (!imagenes.isEmpty()) {
            return ResponseEntity.ok(imagenes.get(0).getRutaImagen());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
 // Mostrar lista y formulario
    @GetMapping
    public String mostrarFormulario(Model model, HttpServletRequest request) {
        model.addAttribute("categoria", new Categoria());
//        model.addAttribute("baseUrl", obtenerBaseUrl(request)); // ✅ Agregado
        return "categorias";
    }
    
    @GetMapping("/buscar")
    public String buscarCategoriaPorId(@RequestParam(name = "id", required = false) Long id, Model model, HttpServletRequest request) {
        List<Categoria> categoria;

        if (id != null) {
            Optional<Categoria> categorias = categoriaRepository.findById(id);
            categoria = categorias.map(Collections::singletonList).orElse(Collections.emptyList());
        } else {
            categoria = categoriaRepository.findAll();
        }

        model.addAttribute("categoria", new Categoria());
        model.addAttribute("listaCategoria", categoria);
 //       model.addAttribute("baseUrl", obtenerBaseUrl(request)); // ✅ Agregado
        return "categorias";
    }

    // Guarda o actualiza la categoría
    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria, HttpServletRequest request) {
        categoriaService.crearActualizarCategoria(categoria);
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        String forwardedProto = request.getHeader("X-Forwarded-Proto");

        String baseUrl;
        if (forwardedHost != null && forwardedProto != null) {
            baseUrl = forwardedProto + "://" + forwardedHost;
        } else {
            baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }

        return "redirect:" + baseUrl + "/categorias";
    }

    // Editar: carga la categoría en el formulario
    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model, HttpServletRequest request) {
        Categoria categoria = categoriaService.getIdCategoria(id);
        model.addAttribute("categoria", categoria);
        model.addAttribute("listaCategoria", categoriaService.listarCategorias());
//        model.addAttribute("baseUrl", obtenerBaseUrl(request)); // ✅ Agregado
        return "categorias";
    }

    // Eliminar categoría
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, HttpServletRequest request) {
        categoriaService.eliminarCategoria(id);
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        String forwardedProto = request.getHeader("X-Forwarded-Proto");

        String baseUrl;
        if (forwardedHost != null && forwardedProto != null) {
            baseUrl = forwardedProto + "://" + forwardedHost;
        } else {
            baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }

        return "redirect:" + baseUrl + "/categorias";
    }

}
