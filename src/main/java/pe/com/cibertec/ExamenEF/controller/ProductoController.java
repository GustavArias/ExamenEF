package pe.com.cibertec.ExamenEF.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ExamenEF.model.entity.DetallePedidoEntity;
import pe.com.cibertec.ExamenEF.model.entity.Pedido;
import pe.com.cibertec.ExamenEF.model.entity.ProductoEntity;
import pe.com.cibertec.ExamenEF.model.entity.UsuarioEntity;
import pe.com.cibertec.ExamenEF.service.ProductoService;
import pe.com.cibertec.ExamenEF.service.UsuarioService;
import pe.com.cibertec.ExamenEF.service.impl.PdfService;

@Controller
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoService productoService;
	private final UsuarioService usuarioService;
	private final PdfService pdfService;
	
	@GetMapping("/menu")
	public String mostrarMenu(Model model, HttpSession session) {
		
		if(session.getAttribute("usuario") == null) {			
			return "redirect:/";
		}
		String correoSesion = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(correoSesion);
		model.addAttribute("foto", usuarioEncontrado.getUrlImagen());
		
		List<ProductoEntity> listaProductos = productoService.buscarTodosProductos();
		model.addAttribute("productos", listaProductos);
		
		List<Pedido>productoSesion = null;
		if(session.getAttribute("carrito") == null){
			productoSesion = new ArrayList<Pedido>();
		}else {
			productoSesion = (List<Pedido>)session.getAttribute("carrito");
		}
		model.addAttribute("cant_carrito", productoSesion.size());
		
		List<DetallePedidoEntity>detallePedidoEntity = new ArrayList<DetallePedidoEntity>();
		Double totalPedido = 0.0;
		
		for(Pedido ped: productoSesion) {
			DetallePedidoEntity detPed = new DetallePedidoEntity();
			ProductoEntity productoEntity = productoService.buscarProductoPorId(ped.getProductoId());
			detPed.setProductoEntity(productoEntity);
			detPed.setCantidad(ped.getCantidad());
			detallePedidoEntity.add(detPed);
			totalPedido += ped.getCantidad() * Double.parseDouble(productoEntity.getPrecio());
		}
		model.addAttribute("carrito", detallePedidoEntity);
		model.addAttribute("total", totalPedido);
		
		
		
		return "menu";
	}
	
	@PostMapping("/agregar_producto")
	public String agregarProducto(HttpSession sesion, @RequestParam("productoId")String proId, @RequestParam("cant")String cant) {
		
		List<Pedido>productosSesion = null;
		if(sesion.getAttribute("carrito") == null){
			productosSesion = new ArrayList<Pedido>();
		}else {
			productosSesion = (List<Pedido>)sesion.getAttribute("carrito");
		}
		
		Integer cantidad = Integer.parseInt(cant);
		Integer productoId = Integer.parseInt(proId);
		Pedido pedidoNuevo = new Pedido(cantidad, productoId);
		productosSesion.add(pedidoNuevo);
		sesion.setAttribute("carrito", productosSesion);
		return "redirect:/menu";
	}
	
	
	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource>generarPDF(HttpSession sesion) throws IOException{
		List<Pedido>productoSesion = null;
    	if(sesion.getAttribute("carrito")  == null) {
    		productoSesion = new ArrayList<Pedido>();
    	}else {
    		productoSesion = (List<Pedido>)sesion.getAttribute("carrito");
    	}
    	List<DetallePedidoEntity>detallePedidoEntities = new ArrayList<DetallePedidoEntity>();
    	Double totalPedido = 0.0;
    	
    	for(Pedido ped: productoSesion) {
    		DetallePedidoEntity det = new DetallePedidoEntity();
    		ProductoEntity productoEntity = productoService.buscarProductoPorId(ped.getProductoId());
    		det.setProductoEntity(productoEntity);
    		det.setCantidad(ped.getCantidad());
    		detallePedidoEntities.add(det);
    		totalPedido += Double.parseDouble(productoEntity.getPrecio()) * ped.getCantidad();

    	}
    	
    	Map<String, Object> datosPdf = new HashMap<String, Object>();
    	datosPdf.put("factura", detallePedidoEntities);
    	datosPdf.put("precio_total", totalPedido);
    	
    	ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-Disposition", "inline; filename=productos.pdf");
    	
    	return ResponseEntity.ok()
    			.headers(headers)
    			.contentType(MediaType.APPLICATION_PDF)
    			.body(new InputStreamResource(pdfBytes));
		
	}
	
	@GetMapping("/detalle_producto/{id}")
	public String verDetalle(Model model, @PathVariable("id")Integer id) {
		ProductoEntity productoEntity = productoService.buscarProductoPorId(id);
		model.addAttribute("user", productoEntity);
		return "detalle_producto";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProducto(@PathVariable("id")Integer id) {
		productoService.eliminarProducto(id);
		return "redirect:/menu";
	}
	
	@GetMapping("/registrar_producto")
	public String mostrarRegistrarProducto(Model model) {
		model.addAttribute("producto", new ProductoEntity());
		return "registrar_producto";
	}
	
	
	
	
}
