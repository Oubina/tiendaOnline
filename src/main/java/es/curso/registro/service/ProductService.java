package es.curso.registro.service;

import java.util.List;

import es.curso.registro.model.Producto;

public interface ProductService {
	
	List<Producto> getAll ();

	List<Producto> getProductByFiltro(String nombre, String descripcion, double precio);


}
