package pe.com.cibertec.ExamenEF.service;

import java.util.List;

import pe.com.cibertec.ExamenEF.model.entity.ProductoEntity;

public interface ProductoService {
	
	void eliminarProducto(Integer id);
	void actualizarProducto(Integer id, ProductoEntity productoEntity);
	void crearProducto(ProductoEntity productoEntity);
	List<ProductoEntity> buscarTodosProductos();
	ProductoEntity buscarProductoPorId(Integer id);
}
