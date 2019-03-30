package es.curso.registro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.curso.registro.model.Producto;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Long>{

}
