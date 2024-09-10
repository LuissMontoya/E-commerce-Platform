package co.com.test.linktic.appEcommerce.service;


import org.springframework.http.ResponseEntity;

import co.com.test.linktic.appEcommerce.DTO.CategoryDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;

public interface ICategoryService {
	
	public ResponseEntity<ResponseDTO> getAll();
	public ResponseEntity<ResponseDTO> saveCategory(CategoryDTO categoryDTO);
	public ResponseEntity<ResponseDTO> updateCategory(CategoryDTO categoryDTO);
	public ResponseEntity<ResponseDTO> findCategoryById(Integer id);
	public ResponseEntity<ResponseDTO> delete(Integer id);
}
