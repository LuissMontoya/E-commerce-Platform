package co.com.test.linktic.appEcommerce.service;


import org.springframework.http.ResponseEntity;

import co.com.test.linktic.appEcommerce.DTO.OrderDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;

public interface IOrderService {
	
	public ResponseEntity<ResponseDTO> getAll();
	public ResponseEntity<ResponseDTO> saveOrder(OrderDTO orderDTO);
	public ResponseEntity<ResponseDTO> updateOrder(OrderDTO orderDTO);
	public ResponseEntity<ResponseDTO> findOrderById(Integer id);
	public ResponseEntity<ResponseDTO> delete(Integer id);
}
