package co.com.test.linktic.appEcommerce.repositories;

import org.springframework.stereotype.Repository;

import co.com.test.linktic.appEcommerce.entity.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer>{
	
	public Optional<Users> findClientById(Integer id);
    List<Users> findAllByOrderByIdDesc();
    Users findByEmail(String email);
}