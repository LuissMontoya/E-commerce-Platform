package co.com.test.linktic.appEcommerce.repositories;

import org.springframework.stereotype.Repository;

import co.com.test.linktic.appEcommerce.entity.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
	
	public Optional<Order> findOrderById(Integer id);
    List<Order> findAllByOrderByIdDesc();
}