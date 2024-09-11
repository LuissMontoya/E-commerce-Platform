package co.com.test.linktic.appEcommerce.mapper;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import co.com.test.linktic.appEcommerce.DTO.OrderDTO;
import co.com.test.linktic.appEcommerce.entity.Order;

@Mapper 
public interface OrdersMapper {
    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);
	
	OrderDTO entityToDTO(Order entity);
	
	@InheritInverseConfiguration 
	Order dtoToEntity(OrderDTO orderDTO);
	List<OrderDTO> listBeanToListDto(List<Order> list);
	List<Order> listDtoToListEnt(List<OrderDTO> list);
	   
}
