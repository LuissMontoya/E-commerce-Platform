package co.com.test.linktic.appEcommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.com.test.linktic.appEcommerce.DTO.UsersDTO;
import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.entity.Users;
import co.com.test.linktic.appEcommerce.mapper.UsersMapper;
import co.com.test.linktic.appEcommerce.repositories.UserRepository;
import co.com.test.linktic.appEcommerce.service.IUserService;
import co.com.test.linktic.appEcommerce.utils.Constants;
import co.com.test.linktic.appEcommerce.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements IUserService{
	
	private final UserRepository userRepository;
	
	@Override
	public ResponseEntity<ResponseDTO> saveUser(UsersDTO userDTO) {
		log.info("save "+ userDTO);
		
		if(Objects.isNull(userDTO.getId())) {
			userDTO.setId(1);
			List<Users> clientes = userRepository.findAllByOrderByIdDesc();
			if(!clientes.isEmpty()) {
				Integer valorAnterior = clientes.get(0).getId();
				userDTO.setId(valorAnterior+1);
			}
		}
		
		ResponseDTO response = null;
		try {
				response = Utils.mapearRespuesta(HttpStatus.CREATED.name(), HttpStatus.CREATED.value(),
						this.userRepository.save(UsersMapper.INSTANCE.dtoToEntity(userDTO)));
			
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
						UsersMapper.INSTANCE.listBeanToListDto(this.userRepository.findAll()));
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ResponseDTO> updateUser(UsersDTO userDTO) {
		log.info("update "+userDTO);
		ResponseDTO response = null;
		Optional<Users> clientOptional = this.userRepository.findById(userDTO.getId());
		try {
			if(clientOptional.isPresent()) {
				response = Utils.mapearRespuesta(HttpStatus.OK.name(), HttpStatus.OK.value(),
						this.userRepository.save(UsersMapper.INSTANCE.dtoToEntity(userDTO)));
			}
			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			response = Utils.mapearRespuesta(e.getLocalizedMessage(), HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> findUserById(Integer id) {
		log.info("find User By Id "+id);
		ResponseDTO response = null;
		Optional<Users> client = this.userRepository.findById(id);
		try {
			if(client.isPresent()) {
				List<Users> clientes = new ArrayList<>();
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
	    Optional<Users> client = this.userRepository.findById(id);
	    
	    try {
	        if (client.isPresent()) {
	            this.userRepository.deleteById(id);
	            
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
