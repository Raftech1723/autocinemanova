package com.pelicula.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelicula.DTO.PeliculaDTO;
import com.pelicula.cliente.CategoriaCliente;
import com.pelicula.entity.Pelicula;
import com.pelicula.repository.PeliculaRepository;

@Service
public class PeliculaServiceImpl implements PeliculaService {
	
	@Autowired
    private PeliculaRepository peliculaRepository;

	@Autowired
	private CategoriaCliente categoriaCliente;
	
	@Override
    public PeliculaDTO obtenerPorId(Long id) {
        Pelicula p = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(p.getId());
        dto.setTitulo(p.getTitulo());
        return dto;
    }

    @Override
    public List<PeliculaDTO> listarTodas() {
        return peliculaRepository.findAll().stream().map(p -> {
            PeliculaDTO dto = new PeliculaDTO();
            dto.setId(p.getId());
            dto.setTitulo(p.getTitulo());
            return dto;
        }).collect(Collectors.toList());
    }

	@Override
	public List<PeliculaDTO> listarPelicula() {
	    // Llama al otro método con baseUrl vacío o default
	    return listarPelicula(""); // O podrías pasar una ruta por defecto
	}
	@Override
	public List<PeliculaDTO> listarPelicula(String baseUrl) {
	    List<Pelicula> peliculas = peliculaRepository.findAll();
	    return peliculas.stream().map(p -> {
	        String rutaImagen = p.getImagen();
	        if (rutaImagen == null || rutaImagen.isEmpty()) {
	            rutaImagen = baseUrl + "/imagen/default.jpg";
	        } else {
	            rutaImagen = baseUrl + "/imagen/" + rutaImagen;
	        }
	        return new PeliculaDTO(p, rutaImagen);
	    }).collect(Collectors.toList());
	}

	@Override
	public List<PeliculaDTO> buscarPorCategoria(Integer categoriaId) {
	    List<Pelicula> peliculas = peliculaRepository.findByCategoriaId(categoriaId); // ✅ sin conversión
	    return peliculas.stream().map(p -> {
	        String ruta = categoriaCliente.imagenPorCategoriaId(p.getCategoriaId());
	        if (ruta == null || ruta.isEmpty()) {
	            ruta = "default.jpg";
	        }
	        return new PeliculaDTO(p, ruta);
	    }).collect(Collectors.toList());
	}



    @Override
    public Pelicula getIdPelicula(Long id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    @Override
    public void crearActualizarPelicula(Pelicula pelicula) {
        peliculaRepository.save(pelicula);
    }

    @Override
    public void eliminarPelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

}