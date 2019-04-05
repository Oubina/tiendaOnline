package es.curso.registro.service;

import java.util.List;

import es.curso.registro.model.Producto;

public interface ProductService {

	List<Producto> getAll();

	List<Producto> getProductByFiltro(String nombre, String descripcion, double precio);

	public void addProducto(Producto producto);

	public void addProducto(String nombre, String descripcion, String marca, double precio, int cantidad);

	public Producto getProductById(Integer id);

	public void updateProducto(Integer idProducto);
	
	public void saveProducto(Producto producto);

}
