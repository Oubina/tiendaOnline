package es.curso.registro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.curso.registro.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Query ("select p from Pedido p where p.estado.idEstado like %?1% and p.comentario like %?2% AND p.usuario.nombre = ?3")
	public List <Pedido> getPedidosByFiltro(String nombre, String comentario,String estado);
}