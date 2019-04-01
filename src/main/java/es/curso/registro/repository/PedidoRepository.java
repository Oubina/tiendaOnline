package es.curso.registro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.curso.registro.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {


}
