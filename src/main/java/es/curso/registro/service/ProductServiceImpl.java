package es.curso.registro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.curso.registro.model.Pedido;
import es.curso.registro.model.Producto;
import es.curso.registro.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Producto> getAll() {
		
		return productRepository.findAll();
	}

	@Override
	public List<Producto> getProductByFiltro(String nombre, String descripcion, double precio) {
		
		return productRepository.findProductByFiltro(nombre, descripcion, precio);
	}

	@Override
	public void addProducto(Producto producto) {
		productRepository.save(producto);
		
	}

	@Override
	public void addProducto(String nombre, String descripcion, String marca, double precio, int cantidad) {
		Producto producto=new Producto(nombre, descripcion, marca, precio, cantidad);
		productRepository.save(producto);
		
	}
	@Override
	public Producto getProductById(Integer id) {
		return productRepository.findById(id).get();
	}
	
	@Override
	public void updateProducto(Integer idProducto) {
		Producto productoUpdateProducto = productRepository.findById(idProducto).get();

		productRepository.save(productoUpdateProducto);

	}
	
	@Override
	
	public void saveProducto(Producto producto) {
		productRepository.save(producto);
	}

}
