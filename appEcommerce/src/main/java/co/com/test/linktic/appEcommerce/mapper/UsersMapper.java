package co.com.test.linktic.appEcommerce.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.test.linktic.appEcommerce.DTO.UsersDTO;
import co.com.test.linktic.appEcommerce.entity.Users;

@Mapper 
public interface UsersMapper {
	
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);
	
	UsersDTO entityToDTO(Users entity);
	
	@InheritInverseConfiguration 
	Users dtoToEntity(UsersDTO userDTO);
	
	List<UsersDTO> listBeanToListDto(List<Users> list);
	
	List<Users> listDtoToListEnt(List<UsersDTO> list);
}
