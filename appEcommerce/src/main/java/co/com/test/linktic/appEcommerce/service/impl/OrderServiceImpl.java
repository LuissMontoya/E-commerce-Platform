package co.com.test.linktic.appEcommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.com.test.linktic.appEcommerce.DTO.OrderDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.entity.Order;
import co.com.test.linktic.appEcommerce.entity.Product;
import co.com.test.linktic.appEcommerce.mapper.OrdersMapper;
import co.com.test.linktic.appEcommerce.repositories.OrderRepository;
import co.com.test.linktic.appEcommerce.repositories.ProductRepository;
import co.com.test.linktic.appEcommerce.service.IOrderService;
import co.com.test.linktic.appEcommerce.utils.Constants;
import co.com.test.linktic.appEcommerce.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@Override
	public ResponseEntity<ResponseDTO> saveOrder(OrderDTO orderDTO) {
		log.info("save " + orderDTO);

		if (Objects.isNull(orderDTO.getId())) {
			orderDTO.setId(1);
			List<Order> ordenes = orderRepository.findAllByOrderByIdDesc();
			if (!ordenes.isEmpty()) {
				Integer valorAnterior = ordenes.get(0).getId();
				orderDTO.setId(valorAnterior + 1);
			}
		}

		ResponseDTO response = null;
		Order order = new Order();
		try {
			order = OrdersMapper.INSTANCE.dtoToEntity(orderDTO);
			log.info("orderDTO.getProducts()");
	        List<Product> products = productRepository.findAllById(orderDTO.getProducts());
	        order.setProducts(products);
	     
			response = Utils.mapearRespuesta(HttpStatus.CREATED.name(), HttpStatus.CREATED.value(),
					this.orderRepository.save(order));

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseDTO> getAll() {
		log.info("getAll ");
		ResponseDTO response = null;
		try {
			response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),this.orderRepository.findAll());
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> updateOrder(OrderDTO orderDTO) {
		log.info("update " + orderDTO);
		ResponseDTO response = null;
		Order order = new Order();
		Optional<Order> orderOptional = this.orderRepository.findById(orderDTO.getId());
		try {
			order = OrdersMapper.INSTANCE.dtoToEntity(orderDTO);
			 List<Product> products = productRepository.findAllById(orderDTO.getProducts());
		        order.setProducts(products);
		        
			if (orderOptional.isPresent()) {
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						this.orderRepository.save(order));
			}

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> findOrderById(Integer id) {
		log.info("find Order By Id " + id);
		ResponseDTO response = null;
		Optional<Order> order = this.orderRepository.findById(id);
		try {
			if (order.isPresent()) {
				List<Order> ordenes = new ArrayList<>();
				ordenes.add(order.get());
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(), ordenes);

			} else {
				response = Utils.mapearRespuesta(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
			}

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> delete(Integer id) {
		log.info("delete " + id);
		ResponseDTO response = null;
		Optional<Order> order = this.orderRepository.findById(id);

		try {
			if (order.isPresent()) {
				this.orderRepository.deleteById(id);

				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						Constants.ELIMINADO_EXITOSAMENTE);

			} else {
				response = Utils.mapearRespuesta(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
						Constants.NINGUN_REGISTRO_ENCONTRADO);
			}

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
