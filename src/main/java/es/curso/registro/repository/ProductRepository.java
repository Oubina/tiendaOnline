package es.curso.registro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.curso.registro.model.Producto;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Integer>{

	@Query("select p from Producto p where (p.nombre like %?1%) and (p.descripcion like %?2%) and (p.precio <?3)")
	List<Producto> findProductByFiltro(String nombre, String descripcion, double precio);
}
