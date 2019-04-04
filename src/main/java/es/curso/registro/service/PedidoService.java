package es.curso.registro.service;

import java.util.List;

import es.curso.registro.model.Estado;
import es.curso.registro.model.Pedido;
import es.curso.registro.model.User;

public interface PedidoService {

	public Pedido findPedidoById(Integer idPedido);

	public List<Pedido> getAll();

	public void addPedido(Pedido pedido);
	
	public void save(Pedido pedido);

	public void addPedido(User usuario, String direccion, String comentario, Estado estado, double precioFinal);

	public void deletePedidoById(Integer idPedido);

	public void updatePedido(Integer idPedido);

	public List<Pedido> getPedidosByFiltro(Integer id_estado);

}
