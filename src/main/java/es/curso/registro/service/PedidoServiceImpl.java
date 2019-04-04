package es.curso.registro.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.curso.registro.model.Estado;
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
//Importante para que estos cambios persistan enn BBDD
	@Override	
	@Transactional
	public void addPedido(Pedido pedido) {
		pedidoRepository.save(pedido);

	}

	@Override
	@Transactional
	public void addPedido(User usuario, String direccion, String comentario, Estado estado, double precioFinal) {
		Pedido pedido = new Pedido(usuario, direccion, comentario, estado);
		pedido.setPrecioFinal(precioFinal);
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

	@Override
	public List<Pedido> getPedidosByFiltro(Integer idEstado) {
		return pedidoRepository.getPedidosByFiltro(idEstado);
	}

	@Override
	@Transactional
	public void save(Pedido pedido) {
		pedidoRepository.save(pedido);
		
	}
	
	

}
