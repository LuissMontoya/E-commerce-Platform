package co.com.test.linktic.appEcommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.test.linktic.appEcommerce.DTO.OrderDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.service.impl.OrderServiceImpl;
import co.com.test.linktic.appEcommerce.utils.Constants;
import co.com.test.linktic.appEcommerce.utils.RespErrorMessage;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT })
@RequiredArgsConstructor
public class OrderController {

	private final OrderServiceImpl orderServiceImpl;

	@ApiResponses(value = {
			@ApiResponse(code = Constants.CODIGO_200, message = Constants.MESG_200, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_422, message = Constants.MESG_422, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_400, message = Constants.MESG_400, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_500, message = Constants.MESG_500, response = RespErrorMessage.class) })
	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> saveOrder(@RequestBody OrderDTO order) {
		return this.orderServiceImpl.saveOrder(order);
	}

	@GetMapping("/search")
	@ApiResponses(value = {
			@ApiResponse(code = Constants.CODIGO_200, message = Constants.MESG_200, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_400, message = Constants.MESG_400, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_422, message = Constants.MESG_422, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_500, message = Constants.MESG_500, response = RespErrorMessage.class) })
	public ResponseEntity<ResponseDTO> findOrderById(@RequestParam("id") Integer id) {
		return this.orderServiceImpl.findOrderById(id);
	}

	@GetMapping("/getAll")
	@ApiResponses(value = {
			@ApiResponse(code = Constants.CODIGO_200, message = Constants.MESG_200, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_400, message = Constants.MESG_400, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_422, message = Constants.MESG_422, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_500, message = Constants.MESG_500, response = RespErrorMessage.class) })
	public ResponseEntity<ResponseDTO> getAll() {
		return this.orderServiceImpl.getAll();
	}

	@PutMapping("/update")
	@ApiResponses(value = {
			@ApiResponse(code = Constants.CODIGO_200, message = Constants.MESG_200, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_400, message = Constants.MESG_400, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_422, message = Constants.MESG_422, response = RespErrorMessage.class),
			@ApiResponse(code = Constants.CODIGO_500, message = Constants.MESG_500, response = RespErrorMessage.class) })
	public ResponseEntity<ResponseDTO> update(@RequestBody OrderDTO order) {
		return this.orderServiceImpl.updateOrder(order);
	}
	
	 @DeleteMapping("/delete")
	    @ApiResponses(value = {
	            @ApiResponse(code = Constants.CODIGO_200, message = Constants.MESG_200, response = RespErrorMessage.class),
	            @ApiResponse(code = Constants.CODIGO_400, message = Constants.MESG_400, response = RespErrorMessage.class),
	            @ApiResponse(code = Constants.CODIGO_422, message = Constants.MESG_422, response = RespErrorMessage.class),
	            @ApiResponse(code = Constants.CODIGO_500, message = Constants.MESG_500, response = RespErrorMessage.class) })
	    public ResponseEntity<ResponseDTO> delete(@RequestParam("id") Integer id) {
	    	return this.orderServiceImpl.delete(id);
	    	}


}
