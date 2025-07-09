package com.pelicula.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/imagen")
public class ImagenController {
	
	
	@GetMapping("/{categoria}/{nombreImagen}")
    public ResponseEntity<Resource> obtenerImagen(
            @PathVariable String categoria,
            @PathVariable String nombreImagen) throws IOException {

        Path ruta = Paths.get("src/main/resources/static/imagen/" + categoria + "/" + nombreImagen);
        Resource recurso = new UrlResource(ruta.toUri());

        if (recurso.exists() && recurso.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // o IMAGE_PNG según el caso
                    .body(recurso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
