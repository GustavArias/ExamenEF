package pe.com.cibertec.ExamenEF.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.ExamenEF.model.entity.UsuarioEntity;
import pe.com.cibertec.ExamenEF.repository.UsuarioRepository;
import pe.com.cibertec.ExamenEF.service.UsuarioService;
import pe.com.cibertec.ExamenEF.utils.Utilitarios;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

	
	private final UsuarioRepository usuarioRepository;
	
	@Override
	public void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto) {
		
		String nombreFoto = Utilitarios.guardarImagen(foto);
		usuarioEntity.setUrlImagen(nombreFoto);
		
		String passwordHash = Utilitarios.extraerHash(usuarioEntity.getPassword());
		usuarioEntity.setPassword(passwordHash);
		
		usuarioRepository.save(usuarioEntity);
	}

	@Override
	public boolean validarUsuario(UsuarioEntity usuarioEntity) {
		
		return false;
	}

	
}
