package co.com.test.linktic.appEcommerce.service.impl;

import org.springframework.http.ResponseEntity;

import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.DTO.UsuarioDto;

public interface IUsuarioService {
	public ResponseEntity<ResponseDTO> auntenticar(final UsuarioDto usuario);// Agregar este metodo para Autenticar el
	public ResponseEntity<ResponseDTO> obtenerUsuarios();
	public ResponseEntity<ResponseDTO> guardarUsuario(UsuarioDto usuario);// Agregar este metodo para guardar el usuario
	public ResponseEntity<ResponseDTO> findUserByName(String nombreUsuario);
}
