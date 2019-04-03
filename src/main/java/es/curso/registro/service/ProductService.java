package es.curso.registro.service;

import java.util.List;

import es.curso.registro.model.Producto;

public interface ProductService {

	List<Producto> getAll();

	List<Producto> getProductByFiltro(String nombre, String descripcion, int precio);

	public void addProducto(Producto producto);

	public void addProducto(String nombre, String descripcion, String marca, int precio, int cantidad);

	public Producto getProductById(Integer id);

}
