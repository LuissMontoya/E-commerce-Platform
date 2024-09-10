package co.com.test.linktic.appEcommerce.service;


import org.springframework.http.ResponseEntity;

import co.com.test.linktic.appEcommerce.DTO.UsersDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;

public interface IUserService {
	
	public ResponseEntity<ResponseDTO> getAll();
	public ResponseEntity<ResponseDTO> saveUser(UsersDTO userDTO);
	public ResponseEntity<ResponseDTO> updateUser(UsersDTO userDTO);
	public ResponseEntity<ResponseDTO> findUserById(Integer id);
	public ResponseEntity<ResponseDTO> delete(Integer id);
}
