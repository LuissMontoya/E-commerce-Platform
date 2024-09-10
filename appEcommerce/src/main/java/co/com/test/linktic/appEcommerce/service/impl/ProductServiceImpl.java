package co.com.test.linktic.appEcommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.com.test.linktic.appEcommerce.DTO.ProductDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.entity.Category;
import co.com.test.linktic.appEcommerce.entity.Product;
import co.com.test.linktic.appEcommerce.mapper.ProductsMapper;
import co.com.test.linktic.appEcommerce.repositories.CategoryRepository;
import co.com.test.linktic.appEcommerce.repositories.ProductRepository;
import co.com.test.linktic.appEcommerce.service.IProductService;
import co.com.test.linktic.appEcommerce.utils.Constants;
import co.com.test.linktic.appEcommerce.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<ResponseDTO> saveProduct(ProductDTO productDTO) {
		log.info("save " + productDTO);

		if (Objects.isNull(productDTO.getId())) {
			productDTO.setId(1);
			List<Product> clientes = productRepository.findAllByOrderByIdDesc();
			if (!clientes.isEmpty()) {
				Integer valorAnterior = clientes.get(0).getId();
				productDTO.setId(valorAnterior + 1);
			}
		}

		ResponseDTO response = null;
		Product product = new Product();
		try {
			Optional<Category> category = categoryRepository.findById(productDTO.getCategory_id());
			product = ProductsMapper.INSTANCE.dtoToEntity(productDTO);
			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				throw new RuntimeException(Constants.CATEGORIA_NO_ENCONTRADA + productDTO.getCategory_id());
			}
			
			log.info("product "+product);
			response = Utils.mapearRespuesta(HttpStatus.CREATED.name(), HttpStatus.CREATED.value(),
					this.productRepository.save(product));

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
			response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),this.productRepository.findAll());
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> updateProduct(ProductDTO productDTO) {
		log.info("update " + productDTO);
		ResponseDTO response = null;
		Product product = new Product();
		Optional<Product> clientOptional = this.productRepository.findById(productDTO.getId());
		try {
			Optional<Category> category = categoryRepository.findById(productDTO.getCategory_id());
			product = ProductsMapper.INSTANCE.dtoToEntity(productDTO);
			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				throw new RuntimeException(Constants.CATEGORIA_NO_ENCONTRADA + productDTO.getCategory_id());
			}
			if (clientOptional.isPresent()) {
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						this.productRepository.save(product));
			}

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> findProductById(Integer id) {
		log.info("find Product By Id " + id);
		ResponseDTO response = null;
		Optional<Product> client = this.productRepository.findById(id);
		try {
			if (client.isPresent()) {
				List<Product> clientes = new ArrayList<>();
				clientes.add(client.get());
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(), clientes);

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
		Optional<Product> client = this.productRepository.findById(id);

		try {
			if (client.isPresent()) {
				this.productRepository.deleteById(id);

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
