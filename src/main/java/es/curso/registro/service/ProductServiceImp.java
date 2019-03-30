package es.curso.registro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.curso.registro.model.Producto;
import es.curso.registro.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Producto> getAll() {
		
		return productRepository.findAll();
	}

}
