package es.curso.registro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.curso.registro.model.LineaCarrito;
import es.curso.registro.model.LineaPedido;
import es.curso.registro.model.Pedido;
import es.curso.registro.model.Producto;
import es.curso.registro.model.Role;
import es.curso.registro.model.User;
import es.curso.registro.service.EstadoService;
import es.curso.registro.service.PedidoService;
import es.curso.registro.service.ProductService;
import es.curso.registro.service.UserService;
import es.curso.registro.util.Constantes;

@Controller
public class MainController {

	List<Producto> listaCarrito = new ArrayList<Producto>();
	List<LineaCarrito> listLineaCarrito = new ArrayList<LineaCarrito>();

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Autowired
	PedidoService pedidoService;

	@Autowired
	EstadoService estadoService;

	@GetMapping("/")
	public String root() {
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}

	@GetMapping("/index")
	public String userIndex2() {
		return "index";
	}

	@GetMapping(value = "/products")
	public String productos(ModelMap model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaProductos", productService.getAll());
		return "productos";
	}

	@GetMapping("/admin/home")
	public String listaUsuarios(Model model) {
		model.addAttribute("listaUsuarios", userService.getAll());
		return "listaUsuarios";
	}

	@GetMapping("/admin/quitarPrivilegios/{id}")
	public String quitarPrivilegiosAdmin(Model model, @PathVariable("id") Long id) {
		User user = userService.findById(id);
		Role roleToDelete = userService.getRoleWithName(Constantes.ROLE_ADMIN);
		userService.deleteRolesWithRoleIdUserId(roleToDelete.getId(), id);
		userService.update(user);
		return "index";
	}

	@GetMapping("/admin/darPrivilegios/{id}")
	public String darPrivilegiosAdmin(Model model, @PathVariable("id") Long id) {
		User user = userService.findById(id);
		Role roleToAdd = userService.getRoleWithName(Constantes.ROLE_ADMIN);
		user.getRoles().add(roleToAdd);
		userService.update(user);
		return "index";
	}

	@GetMapping(value = "/list-Pedidos")
	public String getListaPedidos(ModelMap model) {
		model.addAttribute("pedido", new Pedido());
		model.addAttribute("listaPedidos", pedidoService.getAll());
		model.addAttribute("listaEstados", estadoService.getAll());
		model.addAttribute("listaUsuarios", userService.getAll());
		return "listaPedidos";
	}

//	// Entra para poder filtrar
	@PostMapping(value = "/list-Pedidos")
	public String listEstadoByFiltro(Model model, RedirectAttributes redir, Producto producto, int cantidad,
			double precioFinalLinea, Pedido pedido) {

		LineaPedido lineaPedido = new LineaPedido(producto, cantidad, precioFinalLinea, pedido);

		model.addAttribute("listaEstados", estadoService.getAll());
		model.addAttribute("listaUsuarios", userService.getAll());
		model.addAttribute("listaPedidos", pedidoService.getPedidosByFiltro(pedido.getEstado().getIdEstado()));

		model.addAttribute("lineaPedido", lineaPedido.getPedido());
		model.addAttribute("lineaPedido", lineaPedido.getProducto());
		model.addAttribute("lineaPedido", lineaPedido.getPrecioFinalLinea());
		model.addAttribute("lineaPedido", lineaPedido.getCantidad());

		return "listaPedidos";
	}

//Para poder aplicar los filtros
	@PostMapping(value = "/products")
	public String sendData(Model model, Producto producto) {
		model.addAttribute("listaProductos", productService.getProductByFiltro(producto.getNombre(),
				producto.getDescripcion(), producto.getPrecio()));
		return "productos";
	}

	@GetMapping(value = "/addProducto")
	public String getAddProducto(ModelMap model) {
		model.addAttribute("producto", new Producto());
		return "addProducto";
	}

	// Quitar permiso en SecurityConfiguration para "/addProducto", solo puede
	// añadir el administrador
	// Crea un nuevo producto en la BBDD
	@PostMapping(value = "/addProducto")
	public String addProducto(ModelMap model, String nombre, String descripcion, String marca, int precio, int cantidad,
			RedirectAttributes redir) {
		productService.addProducto(nombre, descripcion, marca, precio, cantidad);
		//redir.addFlashAttribute("creadoOk", Boolean.TRUE);
		return "redirect:/products";
	}

	@GetMapping("/carrito")
	public String carrito(Model model) {
		model.addAttribute("listLineaCarrito", listLineaCarrito);
		return "carrito";
	}

	@PostMapping(value = "/addCarrito")
	public String addCarrito(Model model, Integer idd) {

		Producto producto = productService.getProductById(idd);

		int cantidad = 1;
		LineaCarrito lineaCarrito = new LineaCarrito(producto, cantidad);

		for (int i = 0; i < listLineaCarrito.size(); i++) {
			if (listLineaCarrito.get(i).getProducto().getIdProducto() == (producto.getIdProducto())) {
				listLineaCarrito.get(i).setCantidad(listLineaCarrito.get(i).getCantidad() + 1);
				model.addAttribute("producto", new Producto());
				model.addAttribute("listaProductos", productService.getAll());
				model.addAttribute("listLineaCarrito", listLineaCarrito);
				return "productos";
			}
		}

		listLineaCarrito.add(lineaCarrito);
		listaCarrito.add(producto);
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaProductos", productService.getAll());
		model.addAttribute("listaCarrito", listaCarrito);
		model.addAttribute("listLineaCarrito", listLineaCarrito);

		return "productos";
	}

	@GetMapping(value = "/tramitarPedido")
	public String tramitarPedido(Model model, HttpSession session) {
		Pedido pedido = new Pedido();

		double precioFinal = 0.0D;
//		double precioPorLinea = 0.0D;
		LineaPedido lineaPedido=new LineaPedido();
		for (LineaCarrito lineaCarrito : listLineaCarrito) {
			precioFinal += lineaCarrito.getCantidad() * lineaCarrito.getProducto().getPrecio();
//			precioPorLinea =lineaPedido.getPrecioFinalLinea()*lineaCarrito.getProducto().getPrecio();
		}
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//		lineaPedido.setPrecioFinalLinea(precioPorLinea);
		User user = userService.findByEmail(loggedInUser.getName());
		pedido.setPrecioFinal(precioFinal);
		pedido.setUsuario(user);
		model.addAttribute("carroCompra", pedido);

		model.addAttribute("usuario", user);
		model.addAttribute("precioLinea", lineaPedido);
		model.addAttribute("listaProductos", productService.getAll());
		model.addAttribute("listaCarrito", listLineaCarrito);
		return "tramitarPedido";
	}
	
	@PostMapping(value = "/enviarPedido")
	public String enviarPedido(Model model, String usuario, String dirección, double precioFinal, String comentario) {
		
//		pedidoService.addPedido(usuario, dirección, precioFinal, comentario);
//		model.addAttribute("carroCompra", pedido);
		return null;
		
		
	}
	
//	@GetMapping (value="/tramitarPedido")
//	public String tramitarPedido(Model model, HttpSession session){
//		Pedido pedido = new Pedido ();
//		//List<LineaPedido> lineaPedido = new ArrayList<LineaPedido>();
//		LineaPedido lineaPedido = new LineaPedido();
//		
//		double precioFinal = 0.0D;
//		double precioLinea = 0.0D;
//		
//		for (LineaCarrito lineaCarrito : listLineaCarrito) {
//			precioFinal += lineaCarrito.getCantidad() * Double.valueOf(lineaCarrito.getProducto().getPrecio());
//			precioLinea =  lineaCarrito.getCantidad() * Double.valueOf(lineaCarrito.getProducto().getPrecio());
//			
//			lineaPedido.setPrecioFinalLinea(precioLinea);
//		}
//		
//	    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//	    User user = userService.findByEmail(loggedInUser.getName());
//	    
//		pedido.setPrecioFinal(precioFinal);
//		pedido.setUsuario(user);
//		model.addAttribute("usuario", user);
//		model.addAttribute("pedido", pedido);
//		model.addAttribute("lineaPedido", lineaPedido);
//		return "tramitarPedido";
//	}

	@PostMapping(value = "/formalizarPedido")
	public String tramitarPedido2(Model model, Pedido carroCompra) {
		List<LineaPedido> listaLineaPedido = new ArrayList<LineaPedido>();

		for (LineaCarrito lineaCarrito : listLineaCarrito) {
			LineaPedido linea = new LineaPedido(lineaCarrito.getProducto(), lineaCarrito.getCantidad(),
					lineaCarrito.getProducto().getPrecio() * lineaCarrito.getCantidad(), carroCompra);
			listaLineaPedido.add(linea);
		}
		carroCompra.setListaLineaPedido(listaLineaPedido);
		carroCompra.setDireccion(carroCompra.getUsuario().getDireccion());
		carroCompra.setEstado(estadoService.getEstadoById(1));
		System.out.println(carroCompra);
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(loggedInUser.getName());
		carroCompra.setUsuario(user);
		pedidoService.addPedido(carroCompra);

		return "redirect:/list-Pedidos";

	}

}
