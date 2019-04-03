package es.curso.registro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lineaPedido")
public class LineaPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idLineaPedido")
	private int idLineaPedido;

	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Producto producto;

	@Column(name = "cantidad")
	private int cantidad;

	@Column(name = "precioFinalLinea")
	private double precioFinalLinea;

	@ManyToOne
	@JoinColumn(name = "idPedido")
	private Pedido pedido;

	public int getIdLineaPedido() {
		return idLineaPedido;
	}

	public void setIdLineaPedido(int idLineaPedido) {
		this.idLineaPedido = idLineaPedido;
	}

	public void setPrecioFinalLinea(double precioFinalLinea) {
		this.precioFinalLinea = precioFinalLinea;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioFinalLinea() {
		return precioFinalLinea;
	}

	public LineaPedido() {

	}

	public LineaPedido(Producto producto, int cantidad, double precioFinalLinea, Pedido pedido) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioFinalLinea = precioFinalLinea;
		this.pedido = pedido;
	}

}
