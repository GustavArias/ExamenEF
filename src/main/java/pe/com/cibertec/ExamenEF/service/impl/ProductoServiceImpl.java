package pe.com.cibertec.ExamenEF.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ExamenEF.model.entity.ProductoEntity;
import pe.com.cibertec.ExamenEF.repository.ProductoRepository;
import pe.com.cibertec.ExamenEF.service.ProductoService;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{

	private final ProductoRepository productoRepository;
	
	@Override
	public List<ProductoEntity> buscarTodosProductos() {
		
		return productoRepository.findAll();
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {
		
		return productoRepository.findById(id).get();
	}

	@Override
	public void crearProducto(ProductoEntity productoEntity) {
		// TODO Auto-generated method stub
		productoRepository.save(productoEntity);
	}

	@Override
	public void eliminarProducto(Integer id) {
		// TODO Auto-generated method stub
		ProductoEntity productoEncontrado = buscarProductoPorId(id);
		if(productoEncontrado !=null) {
			productoRepository.delete(productoEncontrado);
		}
		
	}

	@Override
	public void actualizarProducto(Integer id, ProductoEntity productoEntity) {
		// TODO Auto-generated method stub
		
	}
}
