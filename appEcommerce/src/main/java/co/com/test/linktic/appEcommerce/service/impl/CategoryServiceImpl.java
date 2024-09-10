package co.com.test.linktic.appEcommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.com.test.linktic.appEcommerce.DTO.CategoryDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.entity.Category;
import co.com.test.linktic.appEcommerce.mapper.CategoryMapper;
import co.com.test.linktic.appEcommerce.repositories.CategoryRepository;
import co.com.test.linktic.appEcommerce.service.ICategoryService;
import co.com.test.linktic.appEcommerce.utils.Constants;
import co.com.test.linktic.appEcommerce.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements ICategoryService{
	
	private final CategoryRepository CategoryRepository;
	
	@Override
	public ResponseEntity<ResponseDTO> saveCategory(CategoryDTO categoryDTO) {
		log.info("save "+ categoryDTO);
		
		if(Objects.isNull(categoryDTO.getId())) {
			categoryDTO.setId(1);
			List<Category> clientes = CategoryRepository.findAllByOrderByIdDesc();
			if(!clientes.isEmpty()) {
				Integer valorAnterior = clientes.get(0).getId();
				categoryDTO.setId(valorAnterior+1);
			}
		}
		
		ResponseDTO response = null;
		try {
				response = Utils.mapearRespuesta(HttpStatus.CREATED.name(), HttpStatus.CREATED.value(),
						this.CategoryRepository.save(CategoryMapper.INSTANCE.dtoToEntity(categoryDTO)));
			
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
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						CategoryMapper.INSTANCE.listBeanToListDto(this.CategoryRepository.findAll()));
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ResponseDTO> updateCategory(CategoryDTO categoryDTO) {
		log.info("update "+categoryDTO);
		ResponseDTO response = null;
		Optional<Category> clientOptional = this.CategoryRepository.findById(categoryDTO.getId());
		try {
			if(clientOptional.isPresent()) {
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						this.CategoryRepository.save(CategoryMapper.INSTANCE.dtoToEntity(categoryDTO)));
			}
			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> findCategoryById(Integer id) {
		log.info("find Category By Id "+id);
		ResponseDTO response = null;
		Optional<Category> client = this.CategoryRepository.findById(id);
		try {
			if(client.isPresent()) {
				List<Category> clientes = new ArrayList<>();
				clientes.add(client.get());
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						clientes);
			
			}else {
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
	    Optional<Category> client = this.CategoryRepository.findById(id);
	    
	    try {
	        if (client.isPresent()) {
	            this.CategoryRepository.deleteById(id);
	            
	            response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),Constants.ELIMINADO_EXITOSAMENTE);

	        } else {
	            response = Utils.mapearRespuesta(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),Constants.NINGUN_REGISTRO_ENCONTRADO);
	        }

	    } catch (Exception e) {
	        log.error(e.getLocalizedMessage());
	        response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
	        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	    }

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
