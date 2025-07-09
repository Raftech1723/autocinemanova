package com.pelicula.controller;

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

import com.pelicula.DTO.PeliculaDTO;
import com.pelicula.cliente.CategoriaCliente;
import com.pelicula.entity.Pelicula;
import com.pelicula.entity.PeliculaCatalogo;
import com.pelicula.repository.PeliculaCatalogoRepository;
import com.pelicula.repository.PeliculaRepository;
import com.pelicula.service.PeliculaService;



import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {
	
	@Autowired
    private PeliculaService peliculaService;

    @Autowired
    private CategoriaCliente categoriaCliente;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaCatalogoRepository peliculaCatalogoRepository;

    /**
     * Construye dinámicamente la URL base según el puerto.
     */
    private String obtenerBaseUrl(HttpServletRequest request) {
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        String forwardedHost = request.getHeader("X-Forwarded-Host");

        if (forwardedProto != null && forwardedHost != null) {
            return forwardedProto + "://" + forwardedHost;
        }

        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * Muestra todas las películas.
     */
    @GetMapping
    public String mostrarFormulario(Model model, HttpServletRequest request) {
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("baseUrl", obtenerBaseUrl(request)); // ✅ Agregado
        return "peliculas";
    }
    /**
     * Filtra películas por ID de género/categoría.
     */
    @GetMapping("/genero/{id}")
    public String listarCatalogoPorGenero(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        List<PeliculaCatalogo> catalogoFiltrado = peliculaCatalogoRepository.findByCategoriaId(id);

        model.addAttribute("catalogoPeliculas", catalogoFiltrado); // 👉 Mostrar solo catálogo filtrado
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("categorias", categoriaCliente.listarCategorias());
        model.addAttribute("baseUrl", obtenerBaseUrl(request));
        model.addAttribute("mostrarTabla", true);

        return "peliculas"; // misma vista, pero solo muestra catálogo filtrado
    }

    /**
     * Buscar película por ID (opcional).
     */
    @GetMapping("/buscar")
    public String buscarPeliculaPorId(@RequestParam(name = "id", required = false) Long id,
                                      Model model, HttpServletRequest request) {

        List<Pelicula> peliculas;
        if (id != null) {
            Optional<Pelicula> resultado = peliculaRepository.findById(id);
            peliculas = resultado.map(Collections::singletonList).orElse(Collections.emptyList());
        } else {
            peliculas = peliculaRepository.findAll();
        }

        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("listaPeliculas", peliculas);
        model.addAttribute("categorias", categoriaCliente.listarCategorias());
        model.addAttribute("baseUrl", obtenerBaseUrl(request));

        return "peliculas";
    }


    @PostMapping("/seleccionar")
    public String seleccionarDesdeCatalogo(@RequestParam("catalogoId") Long catalogoId, HttpServletRequest request) {
        PeliculaCatalogo catalogo = peliculaCatalogoRepository.findById(catalogoId).orElse(null);
        if (catalogo != null) {
            Pelicula nueva = new Pelicula();
            nueva.setTitulo(catalogo.getTitulo());
            nueva.setDescripcion(catalogo.getDescripcion());
            nueva.setDuracion(catalogo.getDuracion());
            nueva.setCategoriaId(catalogo.getCategoriaId());
            nueva.setImagen(catalogo.getImagen());
            peliculaRepository.save(nueva);
        }
        // 👉 Redirige a vista que muestra las películas ya guardadas
        return "redirect:" + obtenerBaseUrl(request) + "/peliculas";
    }

    /**
     * Guardar o actualizar una película.
     */
    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula, HttpServletRequest request) {
        peliculaService.crearActualizarPelicula(pelicula);
        return "redirect:" + obtenerBaseUrl(request) + "/peliculas";// Muestra también la tabla
    }

    @GetMapping("/guardadas")
    public String mostrarPeliculasGuardadas(Model model, HttpServletRequest request) {
        List<PeliculaDTO> peliculas = peliculaService.listarPelicula();
        model.addAttribute("listaPeliculas", peliculas);
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("categorias", categoriaCliente.listarCategorias());
        model.addAttribute("baseUrl", obtenerBaseUrl(request));
        return "peliculas"; // 👉 puedes usar otra vista o condicionar en la misma
    }

    /**
     * Cargar datos de película para edición.
     */
    @GetMapping("/editar/{id}")
    public String editarPelicula(@PathVariable Long id, Model model, HttpServletRequest request) {
        Pelicula pelicula = peliculaService.getIdPelicula(id);
        String baseUrl = obtenerBaseUrl(request); // ✅

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("listaPeliculas", peliculaService.listarPelicula(baseUrl)); // ✅
        model.addAttribute("categorias", categoriaCliente.listarCategorias());
        model.addAttribute("baseUrl", baseUrl); // ✅

        return "peliculas";
    }


    /**
     * Eliminar película por ID.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarPelicula(@PathVariable Long id, HttpServletRequest request) {
        peliculaService.eliminarPelicula(id);
        return "redirect:" + obtenerBaseUrl(request) + "/peliculas";
    }
}