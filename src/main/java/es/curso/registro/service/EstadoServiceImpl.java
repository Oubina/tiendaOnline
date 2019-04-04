package es.curso.registro.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.curso.registro.model.Estado;
import es.curso.registro.model.Pedido;
import es.curso.registro.repository.EstadoRepository;

@Service
public class EstadoServiceImpl implements EstadoService {

	@Autowired
	EstadoRepository estadoRepositorty;

	@Override
	public Estado getEstadoById(Integer idEstado) {
		return estadoRepositorty.findById(idEstado).get();
	}

	@Override
	public List<Estado> getAll() {

		return estadoRepositorty.findAll();
	}

	@Override
	public void addEstado(List<Pedido> pedidos, String estado) {
		Estado addEstado = new Estado(pedidos, estado);
		estadoRepositorty.save(addEstado);

	}

	@Override
	@Transactional
	public void addEstado(Estado estado) {
		estadoRepositorty.save(estado);

	}

	@Override
	@Transactional
	public void updateEstado(Estado estado) {
		estadoRepositorty.save(estado);

	}

	@Override
	public void deleteEstado(Integer idEstado) {
		Optional<Estado> estadoDelete = estadoRepositorty.findById(idEstado);

		if (estadoDelete.isPresent()) {
			estadoRepositorty.deleteById(idEstado);
		}
	}


}
