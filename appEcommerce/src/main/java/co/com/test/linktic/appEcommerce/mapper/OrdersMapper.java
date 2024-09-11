package co.com.test.linktic.appEcommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.test.linktic.appEcommerce.DTO.OrderDTO;
import co.com.test.linktic.appEcommerce.entity.Order;
import co.com.test.linktic.appEcommerce.entity.Product;

@Mapper 
public interface OrdersMapper {
    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);
	
	OrderDTO entityToDTO(Order entity);
	
	@InheritInverseConfiguration 
	Order dtoToEntity(OrderDTO orderDTO);
	List<OrderDTO> listBeanToListDto(List<Order> list);
	List<Order> listDtoToListEnt(List<OrderDTO> list);
	
    default List<Integer> map(List<Product> products) {
        return products.stream()
                       .map(Product::getId)
                       .collect(Collectors.toList());
    }

    default Integer productToId(Product product) {
        return product.getId();
    }

    default List<Product> mapToProducts(List<Integer> ids) {
        return ids.stream()
                  .map(id -> {
                      Product product = new Product();
                      product.setId(id);
                      return product;
                  })
                  .collect(Collectors.toList());
    } 
}
