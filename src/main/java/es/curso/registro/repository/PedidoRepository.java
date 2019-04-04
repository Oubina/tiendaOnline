package es.curso.registro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.curso.registro.model.Pedido;
import es.curso.registro.model.User;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Query("select p from Pedido p where p.estado.idEstado = ?1")
	List<Pedido> getPedidosByFiltro(Integer idEstado);
	
//	Se utiliza la etiqueta @Transational en vez de esta consulta.
//	@Query("INSERT INTO Pedido  (comentario, direccion,idEstado,usuario,precioFinal) VALUES (%?1%,%?2%,?3,?4,?5)") 
//	List<Pedido> addPedidos(String comentario,String direccion,int estado,User usuario,double precioFinal);
}