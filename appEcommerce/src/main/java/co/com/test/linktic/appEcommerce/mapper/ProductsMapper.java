package co.com.test.linktic.appEcommerce.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.test.linktic.appEcommerce.DTO.ProductDTO;
import co.com.test.linktic.appEcommerce.entity.Product;

@Mapper 
public interface ProductsMapper {
	
    ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);
	
	ProductDTO entityToDTO(Product entity);
	
	@InheritInverseConfiguration 
	Product dtoToEntity(ProductDTO productDTO);
	
	List<ProductDTO> listBeanToListDto(List<Product> list);
	
	List<Product> listDtoToListEnt(List<ProductDTO> list);
}
