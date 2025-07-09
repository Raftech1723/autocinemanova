package com.funciones.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class FuncionDTO {
	
	private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private Long pelicula_id;
    private String tituloPelicula;
    private Long sala_id;
    private String nombreSala;
	public FuncionDTO() {
		super();
	}
	public FuncionDTO(Long id, LocalDate fecha, LocalTime hora, Long pelicula_id, String tituloPelicula, Long sala_id,
			String nombreSala) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.pelicula_id = pelicula_id;
		this.tituloPelicula = tituloPelicula;
		this.sala_id = sala_id;
		this.nombreSala = nombreSala;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public Long getPelicula_id() {
		return pelicula_id;
	}
	public void setPelicula_id(Long pelicula_id) {
		this.pelicula_id = pelicula_id;
	}
	public String getTituloPelicula() {
		return tituloPelicula;
	}
	public void setTituloPelicula(String tituloPelicula) {
		this.tituloPelicula = tituloPelicula;
	}
	public Long getSala_id() {
		return sala_id;
	}
	public void setSala_id(Long sala_id) {
		this.sala_id = sala_id;
	}
	public String getNombreSala() {
		return nombreSala;
	}
	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
    
}
