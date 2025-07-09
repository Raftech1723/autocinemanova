package com.home.Controller;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String home(Model model) {
	    // Obtener la hora en zona horaria de Perú
	    LocalTime hora = ZonedDateTime.now(ZoneId.of("America/Lima")).toLocalTime();
	    String mensaje;

	    if (hora.isBefore(LocalTime.NOON)) {
	        mensaje = "¡Buenos días! Empieza tu día con una película.";
	    } else if (hora.isBefore(LocalTime.of(19, 0))) {
	        mensaje = "¡Buenas tardes! Disfruta de una función espectacular.";
	    } else {
	        mensaje = "¡Buenas noches! Vive la magia del cine bajo las estrellas.";
	    }

	    model.addAttribute("mensaje", mensaje);
	    return "home";
	}

}
