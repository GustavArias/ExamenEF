package pe.com.cibertec.ExamenEF.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ExamenEF.model.entity.PedidoEntity;
import pe.com.cibertec.ExamenEF.repository.PedidoRepository;
import pe.com.cibertec.ExamenEF.service.PedidoService;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

	
	private final PedidoRepository pedidoRepository;
	
	@Override
	public void crearPedido(PedidoEntity pedidoEntity) {
		
		pedidoRepository.save(pedidoEntity);
		
	}

}
