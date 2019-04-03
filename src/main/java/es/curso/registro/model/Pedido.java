package es.curso.registro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idPedido")
	private int idPedido;

	@ManyToOne
	@JoinColumn(name = "usuario")
	private User usuario;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "comentario")
	private String comentario;

	@Column(name = "precioFinal")
	private Double precioFinal;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
	private List<LineaPedido> listaLineaPedido;

	@ManyToOne
	@JoinColumn(name = "idEstado")
	private Estado estado;

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<LineaPedido> getListaLineaPedido() {
		return listaLineaPedido;
	}

	public void setListaLineaPedido(List<LineaPedido> listaLineaPedido) {
		this.listaLineaPedido = listaLineaPedido;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Pedido() {

	}

	public Double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(Double precioFinal) {
		this.precioFinal = precioFinal;
	}

	public Pedido(User usuario, String direccion, String comentario, Estado estado) {
		this.usuario = usuario;
		this.direccion = direccion;
		this.comentario = comentario;
		this.estado = estado;
	}

}
