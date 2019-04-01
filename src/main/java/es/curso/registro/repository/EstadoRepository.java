package es.curso.registro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.curso.registro.model.Estado;

public interface EstadoRepository  extends JpaRepository<Estado, Integer>{

}
