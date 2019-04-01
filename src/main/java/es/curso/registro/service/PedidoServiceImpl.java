package es.curso.registro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.curso.registro.model.Estado;
import es.curso.registro.model.LineaPedido;
import es.curso.registro.model.Pedido;
import es.curso.registro.model.User;
import es.curso.registro.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;

	@Override
	public Pedido findPedidoById(Integer idPedido) {
		return pedidoRepository.findById(idPedido).get();
	}

	@Override
	public List<Pedido> getAll() {

		return pedidoRepository.findAll();
	}

	@Override
	public void addPedido(Pedido pedido) {
		pedidoRepository.save(pedido);

	}

	@Override
	public void addPedido(User usuario, String direccion, String comentario, List<LineaPedido> listaLineaPedido,
			Estado estado) {
		Pedido pedido = new Pedido(usuario, direccion, comentario, listaLineaPedido, estado);
		pedidoRepository.save(pedido);

	}

	@Override
	public void deletePedidoById(Integer idPedido) {
		Optional<Pedido> pedidoDelete = pedidoRepository.findById(idPedido);

		if (pedidoDelete.isPresent()) {
			pedidoRepository.deleteById(idPedido);
		}

	}

	@Override
	public void updatePedido(Integer idPedido) {
		Pedido pedidoUpdatePedido = pedidoRepository.findById(idPedido).get();

		pedidoRepository.save(pedidoUpdatePedido);

	}

}
