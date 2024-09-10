package co.com.test.linktic.appEcommerce.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.test.linktic.appEcommerce.DTO.CategoryDTO;
import co.com.test.linktic.appEcommerce.entity.Category;

@Mapper 
public interface CategoryMapper {
	
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
    CategoryDTO entityToDTO(Category entity);
	
	@InheritInverseConfiguration 
	Category dtoToEntity(CategoryDTO categoryDTO);
	
	List<CategoryDTO> listBeanToListDto(List<Category> list);
	
	List<Category> listDtoToListEnt(List<CategoryDTO> list);
}
