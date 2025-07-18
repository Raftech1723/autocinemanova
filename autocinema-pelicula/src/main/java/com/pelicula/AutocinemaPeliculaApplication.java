package com.pelicula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.pelicula.cliente")
public class AutocinemaPeliculaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutocinemaPeliculaApplication.class, args);
	}

}
