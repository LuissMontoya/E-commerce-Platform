package co.com.test.linktic.appEcommerce.service;


import org.springframework.http.ResponseEntity;

import co.com.test.linktic.appEcommerce.DTO.ProductDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;

public interface IProductService {
	
	public ResponseEntity<ResponseDTO> getAll();
	public ResponseEntity<ResponseDTO> saveProduct(ProductDTO productDTO);
	public ResponseEntity<ResponseDTO> updateProduct(ProductDTO productDTO);
	public ResponseEntity<ResponseDTO> findProductById(Integer id);
	public ResponseEntity<ResponseDTO> delete(Integer id);
}
