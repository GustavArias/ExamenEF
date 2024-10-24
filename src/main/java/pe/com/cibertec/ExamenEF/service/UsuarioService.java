package pe.com.cibertec.ExamenEF.service;

import org.springframework.web.multipart.MultipartFile;

import pe.com.cibertec.ExamenEF.model.entity.UsuarioEntity;

public interface UsuarioService {

	void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto);
	boolean validarUsuario(UsuarioEntity usuarioEntity);
}