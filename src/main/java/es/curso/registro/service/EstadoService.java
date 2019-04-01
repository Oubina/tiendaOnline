package es.curso.registro.service;

import java.util.List;

import es.curso.registro.model.Estado;
import es.curso.registro.model.Pedido;

public interface EstadoService {

	Estado getEstadoById(Integer idEstado);

	List<Estado> getAll();

	public void addEstado(List<Pedido> pedidos, String estado);

	public void addEstado(Estado estado);

	public void updateEstado(Estado estado);

	public void deleteEstado(Integer idEstado);

}
