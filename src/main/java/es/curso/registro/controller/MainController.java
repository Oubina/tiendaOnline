package es.curso.registro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.curso.registro.model.Estado;
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

//	@RequestMapping("/carrito")
//	public String overview(HttpSession session, List<LineaPedido> listaCarrito) {
//
//		session.setAttribute("listaCarrito", listaCarrito);
//		return "/carrito";
//	}

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
	public String listEstadoByFiltro(Model model, Pedido pedido) {
		model.addAttribute("listaEstados", estadoService.getAll());
		model.addAttribute("listaUsuarios", userService.getAll());
		model.addAttribute("listaPedidos", pedidoService.getPedidosByFiltro(pedido.getEstado().getIdEstado()));
		return "listaPedidos";
	}

	@PostMapping(value = "/products")
	public String sendData(Model model, Producto producto) {
		model.addAttribute("listaProductos", productService.getProductByFiltro(producto.getNombre(),
				producto.getDescripcion(), producto.getPrecio()));
		return "productos";
	}

	@GetMapping(value = "/carrito")
	public String getAddPedido(ModelMap model) {
		model.addAttribute("pedido", new Pedido());
		return "carrito";
	}

	@PostMapping(value = "/carrito")
	public String addPedido(ModelMap model, User usuario, String direccion, String comentario,
			List<LineaPedido> listaLineaPedido, Estado estado, RedirectAttributes redir) {
		
		pedidoService.addPedido(usuario, direccion, comentario, listaLineaPedido, estado);
		redir.addFlashAttribute("creadoOk", Boolean.TRUE);
		return "redirect:/listaPedidos";
	}

	@GetMapping(value = "/addProducto")
	public String getAddProducto(ModelMap model) {
		model.addAttribute("producto", new Producto());
		return "addProducto";
	}

	// Quitar permiso en SecurityConfiguration para "/addProducto", solo puede
	// añadir el administrador
	@PostMapping(value = "/addProducto")
	public String addProducto(ModelMap model, String nombre, String descripcion, String marca, String precio,
			int cantidad, RedirectAttributes redir) {
		productService.addProducto(nombre, descripcion, marca, precio, cantidad);
		redir.addFlashAttribute("creadoOk", Boolean.TRUE);
		return "redirect:/products";
	}
}
